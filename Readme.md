# Employee Information: A Java Packages and Objects Lesson

This lesson introduces a small Java application made of four classes in separate
packages. The `Employee` class creates an object of each class and calls its
method to display a name.

Use the lesson to practise organizing Java files, importing classes, creating
objects, calling instance methods, and compiling a multi-file program.

## Learning goals

By the end of the lesson, you should be able to:

- Explain the difference between a class and an object.
- Describe what a package and an import do.
- Create objects and call instance methods.
- Recognize the role of the `main` method.
- Compile several Java source files and run a class by its fully qualified name.

## Before you begin

You need:

- A JDK 25 installation.
- A terminal opened at the project root (the directory containing `src`).
- A text editor or Java IDE.

The program uses `var`, which is available from Java 10 onward. The compile
command in this lesson uses `--release 25` to target Java 25.

## 1. Explore the project

The Java source files are arranged by package:

```text
EmployeeInformation/
└── src/
    └── org/
        ├── client/
        │   └── Client.java
        ├── company/
        │   └── Company.java
        ├── emp/
        │   └── Employee.java
        └── project/
            └── Project.java
```

In Java, a package declaration corresponds to the folders beneath the source
root. For example, `src/org/company/Company.java` begins with
`package org.company;`.

Each public top-level class is in a file with the same name, including
capitalization: `Company` is declared in `Company.java`.

| Package | Class | Method |
|---|---|---|
| `org.emp` | `Employee` | `empName()` |
| `org.company` | `Company` | `companyName()` |
| `org.client` | `Client` | `clientName()` |
| `org.project` | `Project` | `projectName()` |

## 2. Read the supporting classes

Each supporting class has one public instance method. Calling the method prints
that class's information.

### `Company.java`

```java
package org.company;

public class Company {
    public void companyName() {
        System.out.println("Company Name: Test Solution");
    }
}
```

### `Client.java`

```java
package org.client;

public class Client {
    public void clientName() {
        System.out.println("Client Name: Divine Andy");
    }
}
```

### `Project.java`

```java
package org.project;

public class Project {
    public void projectName() {
        System.out.println("Project Name: Inventory Management");
    }
}
```

These are instance methods, not `static` methods. An instance method is called
through an object of its class. For example, after creating a `Company` object,
the program can call `company.companyName()`.

## 3. Understand the `Employee` class

`Employee` is the entry point for this program. Its `main` method creates four
objects and calls one method on each:

```java
package org.emp;

import org.client.Client;
import org.company.Company;
import org.project.Project;

public class Employee {
    public void empName() {
        System.out.println("Employee Name: Chris Gloria");
    }

    public static void main(String[] argd) {
        var employee = new Employee();
        var client = new Client();
        var company = new Company();
        var project = new Project();

        employee.empName();
        company.companyName();
        client.clientName();
        project.projectName();
    }
}
```

### What is happening?

1. The `package` declaration places `Employee` in `org.emp`.
2. The three `import` statements let this file refer to the other classes by
   their short names.
3. Each `new` expression creates an object. For example,
   `new Company()` creates a `Company` object.
4. Each method call uses the object before the dot. For example,
   `company.companyName()` calls `companyName()` on the `company` object.
5. The calls run from top to bottom, so their order determines the order of the
   printed lines.

The method parameter name `argd` could also be named `args`; it is a variable
name and does not change how Java starts the program.

### Class, object, and method

- A **class** is a definition, such as `Company`.
- An **object** is an instance created from a class, such as the object stored
  in `company`.
- A **method** is an operation a class makes available, such as
  `companyName()`.

Creating an object does not automatically call its instance methods. The
program must call each method explicitly.

### Why use imports?

An import makes a class's short name available in the current source file:

```java
import org.company.Company;
```

Without that import, the program could refer to the class by its fully
qualified name, `org.company.Company`. An import does not create an object,
copy code, or run a method.

`Employee` does not import itself because it is already in the current source
file. Although the packages share the `org` prefix, `org.emp` and
`org.company` are separate packages.

### Why are the classes and methods public?

`Employee` is in a different package from `Company`, `Client`, and `Project`.
Those classes and the methods called from `Employee` are declared `public` so
they can be accessed across package boundaries.

Each supporting class declares no constructor. Java supplies a no-argument
constructor, which allows the program to create an object with `new Company()`
(and similarly for `Client` and `Project`).

### What does `var` mean?

In a local variable declaration such as:

```java
var company = new Company();
```

the compiler infers that `company` has type `Company`. Java is still
statically typed: after this declaration, `company` cannot be used as an
unrelated type.

## 4. Compile the program

From the project root, compile all four source files:

```bash
javac --release 25 -d out \
    src/org/emp/Employee.java \
    src/org/company/Company.java \
    src/org/client/Client.java \
    src/org/project/Project.java
```

Here is what the options mean:

- `javac` starts the Java compiler.
- `--release 25` compiles against the Java 25 language and platform APIs.
- `-d out` writes compiled `.class` files into the `out` directory.
- The paths at the end identify all four source files for compilation.

The backslash (`\`) continues the command on the next line in Bash. When
typing the command on one line, remove the backslashes and line breaks.

The compiler places the class files under `out` using the package structure:

```text
out/
└── org/
    ├── client/Client.class
    ├── company/Company.class
    ├── emp/Employee.class
    └── project/Project.class
```

Compiling all four files together lets the compiler resolve references between
them. The source files stay under `src`; generated class files go under `out`.

## 5. Run the program

Run the `Employee` class by its fully qualified name:

```bash
java -cp out org.emp.Employee
```

- `-cp out` tells Java to look for compiled classes in `out`.
- `org.emp.Employee` is the fully qualified name of the class containing
  `main`.

Expected output:

```text
Employee Name: Chris Gloria
Company Name: Test Solution
Client Name: Divine Andy
Project Name: Inventory Management
```

Only `Employee` needs a `main` method. The other classes provide behavior that
the entry point uses. If you change a source file, save it and compile the
program again before running it.

## 6. Common mistakes

| Message or symptom | What to check |
|---|---|
| `package org.company does not exist` | Compile from the project root and include `Company.java` in the compile command. |
| `cannot find symbol: class Company` | Check the import, package declaration, spelling, and capitalization. |
| A class or method cannot be accessed from outside its package | Check that the class and method are declared `public`. |
| A non-static method is referenced from a static context | Create an object and call the method through it, such as `company.companyName()`. |
| Java cannot find the main class | Confirm that compilation succeeded and that the run command uses `-cp out org.emp.Employee`. |
| The program prints fewer than four lines | Check that all four method calls are in `main`, then save and recompile. |
| `javac` reports a class/file name mismatch | Make the public class name and filename match exactly, including capitalization. |

## 7. Practise and check your understanding

Try these small changes in your own copy of the project:

1. Change one printed name. Compile and run the program again.
2. Move the `client.clientName()` call. How does the output order change?
3. Write one local variable with an explicit type, such as
   `Company company = new Company();`. How is it different from using `var`?
4. Remove one method call but leave its object creation. What is the effect on
   the output?

Check your understanding:

1. Does `import org.company.Company;` create a `Company` object? **No.** It
   lets the source file use the class's short name.
2. Why does the program create `Employee` inside `main`? **`main` is static,**
   so it can run before an `Employee` object exists. The exercise explicitly
   creates that object and then calls its instance method.
3. Why is `company.companyName()` an instance-method call? **The method is
   called through the `company` object.**
4. What decides the order of the output? **The order of the method calls in
   `main`.**
5. Does this program use inheritance? **No.** It creates objects of separate
   classes and calls their methods; none of the classes extends another.

## Summary

This program separates employee, company, client, and project behavior into
different classes and packages. `Employee.main` imports the supporting types,
creates one object of each class, and calls their methods. Compiling the source
files together and running the entry-point class brings the pieces together.

---

**Done by Ushindi B. Victoire**

Software QA Engineer | Requirement Specialist | @ub-victoire | @ub-victor
