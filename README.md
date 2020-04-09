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
