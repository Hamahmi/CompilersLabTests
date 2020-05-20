# task10Test
same as task9Test
```
python task10Test.py -n T07_37_1234_John_Smith -r -s 10 -p
```


# task9Test
same as task8Test but without -t, and added -p for printing the test cases.
```
python task9Test.py -n T07_37_1234_John_Smith -r -s 10 -p
```

# task8Test
put task8Test in the same dir as your T07_37_1234_John_Smith.g4 and antlr, after running antlr and compiled.
run the following :
```
python task8Test.py -n T07_37_1234_John_Smith
```
for the basic test set (the test cases from the task are present)

or if you want a random test cases put the flag -r, and also you can specify the number of test cases using -s Number
```
python task8Test.py -n T07_37_1234_John_Smith -r -s 23
```

```
python task8Test.py -h
usage: task8Test.py [-h] --name NAME [--random] [--size SIZE]

Task 8 Test

optional arguments:
  -h, --help            show this help message and exit
  --name NAME, -n NAME  Your file name : T07_37_1234_John_Smith
  --random, -r          add -r or --random to get randomized test cases
  --size SIZE, -s SIZE  if you used the random option, you can set the number
```

# task7Test

clone the repo, cd to the repo and run the following :
```
python textReplacer.py -n "your_Class_Name"
```
Or just simply open task7Test.java and search and replace each occurance of Tutorial_ID_Name with your class name.

Then put task7Test.java in the same dir as your file for task7 and run :D !


# task6Test

Add task6Test.java to the same dir as your task6.java file and run.

 For those who have static values in their CFG class and some test cases differ from their output, you have 2 solutions, either remove the static (no idea why you need the static vars tbh) or add the following to your task6Test.java :
```
	static Class<?> staticClass = CFG.class;
	static Map<Field, Object> defaultFieldVals = new HashMap<Field, Object>();

	static Object tryClone(Object v) throws Exception {
		if (v instanceof Cloneable) {
			return v.getClass().getMethod("clone").invoke(v);
		}
		return v;
	}

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		Field[] allFields = staticClass.getDeclaredFields();
		try {
			for (Field field : allFields) {
				if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
					Object value = tryClone(field.get(null));
					defaultFieldVals.put(field, value);
				}
			}
		} catch (IllegalAccessException e) {
			System.err.println(e);
			System.exit(1);
		}
	}

	@AfterAll
	public static void tearDownAfterClass() {
		defaultFieldVals = null;
	}

	@BeforeEach
	public void setUp() throws Exception {
		// Reset all static fields
		for (Map.Entry<Field, Object> entry : defaultFieldVals.entrySet()) {
			Field field = entry.getKey();
			Object value = entry.getValue();
			Class<?> type = field.getType();
			// Primitive types
			if (type == Integer.TYPE) {
				field.setInt(null, (Integer) value);
			}
			// ... all other primitive types need to be handled similarly
			// so if you have in your CFG a static float for example you need to put it here
			// All object types
			else {
				field.set(null, tryClone(value));
			}
		}
	}

```
I'm using JUnit5 so if you are using JUnit4 use @Before instead of @BeforeEach, and use @BeforeClass instead of @BeforeAll, and @AfterClass instead of @AfterAll
