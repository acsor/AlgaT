# Contributing to AlgaT

Any contributor producing code on AlgaT is expected to:

* Keep any line length within `80` characters. You are advised to setup an
automatic threshold wrapper
* Declare local variables within methods before any other code, that is:

	```
	public void f (int arg1) {
		int a, b;
		bool x;

		a = initialize();
		b = expensiveOperation(a);

		x = a < b;
		...
	}
	```

	and not

	```
	public void f (int arg1) {
		int a, b;

		a = initialize();
		b = expensiveOperation(a);

		// NOT this way
		bool x = a < b;
		...
	}
	```
* Use the `UpperCamelCase` convention for class and interface names, and
`lowerCamelCase` for function and variable names
* Mark code that needs further reviews, refinement or editing with a `TO-DO`, as
in:

	```
	public void g () {
		// TO-DO Might improve this in the future
		return h();
	}
	```
