# Nested Test Class Screenshot Fix

## Problem

Screenshots were working for regular test classes but NOT for `@Nested` test classes like the invalid add customer scenarios in `AddCustomerTest`.

## Root Cause

When using `@Nested` test classes in JUnit 5:
- The test instance is NOT an instance of `SetUp`
- The test instance is an instance of the nested class (e.g., `AddCustomerTest.InvalidCustomerTests`)
- The nested class has a hidden reference to its outer instance (the `SetUp` class)
- Our original code only checked `if (testInstance instanceof SetUp)`, which failed for nested classes

## Solution

Use Java reflection to access the outer instance from nested test classes:

```java
// For nested test classes, we need to get the outer instance (SetUp)
if (testInstance instanceof SetUp) {
    // Direct instance of SetUp
    SetUp setUp = (SetUp) testInstance;
    driver = setUp.driver;
} else {
    // Nested test class - get the outer instance
    Class<?> testClass = testInstance.getClass();
    
    // Check if this is a nested class
    if (testClass.isMemberClass() && !java.lang.reflect.Modifier.isStatic(testClass.getModifiers())) {
        // Get the outer instance using reflection
        java.lang.reflect.Field outerField = testClass.getDeclaredField("this$0");
        outerField.setAccessible(true);
        Object outerInstance = outerField.get(testInstance);
        
        if (outerInstance instanceof SetUp) {
            SetUp setUp = (SetUp) outerInstance;
            driver = setUp.driver;
        }
    }
}
```

## How It Works

1. **Check if direct SetUp instance**: First, try the simple case where the test class directly extends `SetUp`
2. **Detect nested class**: Use `isMemberClass()` to check if it's a nested class
3. **Access outer instance**: Java stores the outer instance reference in a synthetic field called `this$0`
4. **Get WebDriver**: Once we have the outer `SetUp` instance, we can access the `driver` field

## Verification

After the fix:
- ✅ Regular test classes: Screenshots work
- ✅ Nested test classes: Screenshots work
- ✅ Invalid Add Customer scenarios: Screenshots now captured successfully

Example from test results:
```json
"attachments":[{
  "name":"Should reject invalid customer: Jane Smith! (67890)_20260225_204024_404.png",
  "source":"65b1e1a2-7fe9-4336-82e8-a6bbb43d721f-attachment.png",
  "type":"image/png"
}]
```

## Technical Details

### Java Nested Class Internals

When you create a non-static nested class in Java:
```java
public class Outer {
    class Inner {
        // Inner has implicit reference to Outer
    }
}
```

The compiler generates:
```java
public class Outer$Inner {
    final Outer this$0;  // Synthetic field holding outer instance
    
    Outer$Inner(Outer outer) {
        this.this$0 = outer;
    }
}
```

Our fix uses reflection to access this synthetic `this$0` field.

### Why This Matters for Testing

JUnit 5's `@Nested` annotation creates non-static nested classes to allow:
- Sharing setup/teardown from outer class
- Organizing related tests together
- Accessing outer class fields and methods

But this means the test instance passed to extensions is the nested class, not the outer class, requiring our reflection-based solution.

## Files Modified

- `src/test/java/com/automation/base/SetUp.java`: Enhanced `handleTestExecutionException()` to support nested test classes

## Benefits

- Works with all test structures (regular and nested)
- No changes needed to existing test classes
- Maintains backward compatibility
- Handles edge cases gracefully with try-catch
