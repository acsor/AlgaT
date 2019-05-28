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
* Mark code that needs further reviews, refinement or editing with a `TO-DO`
comment, as in:

	```
	public void g () {
		// TO-DO Might improve this in the future
		return h();
	}
	```

## Git Workflow
By the term __git workflow__ we mean a small set of rules standardizing the way
two or more contributors share their work in a project. Many distributed
workflows are out there, including [this
one](https://git-scm.com/book/en/v2/Git-Branching-Branching-Workflows), but
since no one really appears to be better than another, I'll propose what best
fits the purposes for this project.

The AlgaT workflow scheme is composed of these guidelines:
* So called integration branches are `master`, `testing` and `exchange`.
  `master` tracks content that has been used for long and proved stable;
  `testing` code that needs further verification before integration into
  `master`; `exchange` is a pseudo-temporary branch where content between
  several topic branches (see below) is merged into
* Work that relates to a specific feature or bug fix is to be contained in so
  called "topic branches". Once work on a topic branch is deemed secure, it can
  be merged into `testing`, but in no occasion can a direct merge from a topic
  branch to `master` be performed
* Integration branches `master`, `testing` and `exchange` are the only online
  branches. Topic branches are to be handled locally, without remote
  counterparts
* If a topic branch `t1` needs content from another topic branch `t2`, merging
  `t2` directly into `t1` is ***not*** encouraged; instead:
  1. `exchange` should be updated with the latest content from `testing`
  2. `t2` be merged into `exchange`
  3. `t1` draw content from `exchange` (a `git rebase` is highly recommended)
* Perform a `git rebase` only on branches that haven't yet been pushed
  remotely. See the very end of [3.6 Git Branching -
  Rebasing](https://git-scm.com/book/en/v2/Git-Branching-Rebasing)

## Useful resources

* The publicly free [Java Tutorials](https://docs.oracle.com/javase/tutorial/).
Take those with a grain of salt, as they refer to JDK 8 and previous releases
* Concerning JavaFX:
	* [The JavaFX
	Tutorial](https://docs.oracle.com/javase/8/javase-clienttechnologies.htm)
	(see also the [PDF
	form](https://docs.oracle.com/javase/8/javafx/JFXST.pdf))
	* [Introduction to
	FXML](https://docs.oracle.com/javase/8/javafx/api/javafx/fxml/doc-files/introduction_to_fxml.html).
	I warmly recommend reading Chapter 6 of the PDF form of the above resource
	(only ten pages) before moving on to this
	* [openjfx.io](https://openjfx.io/), for up-to-date info on JavaFX setup
	and usage
* [Pro Git](https://git-scm.com/book/en/v2), for your Git literacy
