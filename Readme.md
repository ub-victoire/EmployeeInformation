# Session 3 — EmployeeInformation

## 1. Understand the assignment

This project contains **four classes in four packages**. The program starts in `Employee`, which creates one object of each class and calls the corresponding method.

| Package | Public class | Required instance method |
|---|---|---|
| `org.emp` | `Employee` | `empName()` |
| `org.company` | `Company` | `companyName()` |
| `org.client` | `Client` | `clientName()` |
| `org.project` | `Project` | `projectName()` |

**Our interpretation:** “Create an object for all 4 classes inside the Employee class” means creating those objects inside `Employee.main`. This includes an `Employee` object, not only objects of the other three classes.

The assignment does not specify names to print. We will use fictional demonstration values.

### What is new?

You already know classes, object creation, instance methods, and console output. Now you will learn:

- Organizing several classes in one project.
- Using packages to group classes.
- Importing classes from other packages.
- Compiling multiple source files together.
- Choosing one class as the program's entry point.

You do **not** need inheritance, Maven, external libraries, or Java's module system for this exercise.

## 2. Create the sibling project

Starting from the `CompanyDetails` directory shown in your terminal:

```bash
cd ..
pwd
mkdir -p EmployeeInformation/src/org/{emp,company,client,project}
mkdir -p EmployeeInformation/.vscode
cd EmployeeInformation
code .
```

The braces in the `mkdir` command are **Bash brace expansion**: Bash expands them into four directory paths. This is shell syntax, not Java syntax.

Your projects should be siblings:

```text
Introduction/
├── EmployeeDetails/
├── CompanyDetails/
└── EmployeeInformation/
```

This project has its **own** `Employee` class. Do not copy or edit Question 01's repository by mistake. Reusing the fully qualified name `org.emp.Employee` in these separate exercises is fine because we compile and run each project independently.

### Source structure

```text
EmployeeInformation/
├── .gitignore
├── README.md
├── .vscode/
│   └── settings.json
└── src/
    └── org/
        ├── emp/
        │   └── Employee.java
        ├── company/
        │   └── Company.java
        ├── client/
        │   └── Client.java
        └── project/
            └── Project.java
```

### VS Code settings

Create `.vscode/settings.json`:

```json
{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "out"
}
```

Keep using your installed JDK 25. No new extension or build tool is required.

### Git ignore rules

Create `.gitignore`:

```gitignore
# Compiled Java files and build output
*.class
out/
bin/
target/

# Editor backup files
*~

# Logs
*.log
```

## 3. Write the three supporting classes first

Each class goes in its own file, and its public class name must match its filename exactly.

### Company.java

Location: `src/org/company/Company.java`

```java
package org.company;

public class Company {

    public void companyName() {
        System.out.println("Company Name: Example Solutions");
    }
}
```

### Client.java

Location: `src/org/client/Client.java`

```java
package org.client;

public class Client {

    public void clientName() {
        System.out.println("Client Name: Example Retail");
    }
}
```

### Project.java

Location: `src/org/project/Project.java`

```java
package org.project;

public class Project {

    public void projectName() {
        System.out.println("Project Name: Inventory Management");
    }
}
```

These classes do not need their own `main` methods. They define behavior that the `Employee` entry point will use.

## 4. Write Employee.java

Location: `src/org/emp/Employee.java`

```java
package org.emp;

import org.client.Client;
import org.company.Company;
import org.project.Project;

public class Employee {

    public void empName() {
        System.out.println("Employee Name: Alex Morgan");
    }

    public static void main(String[] args) {
        var employee = new Employee();
        var company = new Company();
        var client = new Client();
        var project = new Project();

        employee.empName();
        company.companyName();
        client.clientName();
        project.projectName();
    }
}
```

This is the core of the assignment: **four object creations and four method calls inside `Employee.main`.**

We continue using `var` for local variables whose types are clear from their initializers. `company` still has a fixed compile-time type: `Company`. This is not dynamically typed code.

The conventional `public static void main(String[] args)` remains valid on Java 25. We retain it so the entry point does not require an implicitly created launcher object; all four exercise objects are created explicitly in the code above.

## 5. Understand packages, imports, and access

### A package identifies where a class belongs

```java
package org.emp;
```

`Employee` belongs to `org.emp`. It does not also belong to the other three packages.

Each source file has its own package declaration. Do not place four package declarations in one Java file.

### An import lets you use a class's short name

```java
import org.company.Company;
```

This lets you write:

```java
var company = new Company();
```

Without that import, you could instead use the fully qualified class name:

```java
var company = new org.company.Company();
```

These are alternatives; do not duplicate the object declaration.

An import does **not** copy source code, create an object, or execute a method. It makes a type name available to the compiler under its simple name.

`Employee` needs no import for itself. The other classes need imports here because they belong to different packages and we refer to them by their simple names.

Also, `org.emp` and `org.company` are distinct packages. Their shared `org` prefix does not grant special access between them.

### Why public matters

```java
public class Company {

    public void companyName() {
        // ...
    }
}
```

Both the class and the method are `public` so code in `org.emp` can access them.

If you remove `public` from the class, the top-level class becomes package-private. If you remove `public` from the method, that method becomes package-private. Either change prevents this cross-package usage.

Because these classes declare no constructors, Java supplies default no-argument constructors. For these public classes, those default constructors are public too, allowing `new Company()` and the other cross-package object creations.

### Why this is not inheritance

```java
var company = new Company();
company.companyName();
```

`Employee` uses another class by creating an object and calling its method. There is no `extends` declaration and no inheritance requirement here. Do not make `Employee` extend `Company` merely to call `companyName()`.

## 6. Compile all four source files

Save every file. Open a terminal at the **EmployeeInformation project root**.

Run:

```bash
javac --release 25 -d out \
    src/org/emp/Employee.java \
    src/org/company/Company.java \
    src/org/client/Client.java \
    src/org/project/Project.java
```

The backslash continues the shell command onto the next line. It must be the last character on its line: do not put spaces after it.

This is one compiler command with four source files. Their order does not matter here; the compiler resolves the references between them.

`--release 25` explicitly targets Java 25. `-d out` directs generated bytecode into `out/`, preserving the package layout.

Expected generated structure:

```text
out/
└── org/
    ├── emp/
    │   └── Employee.class
    ├── company/
    │   └── Company.class
    ├── client/
    │   └── Client.class
    └── project/
        └── Project.class
```

No `.class` files should be generated under `src/` by this command.

### Why compile all four explicitly?

Compiling only `Employee.java` without configuring a source search path may fail to locate the other source files under `src`. Passing all four files is explicit and easy to understand for this small project.

## 7. Run the entry-point class

```bash
java -cp out org.emp.Employee
```

Expected output:

```text
Employee Name: Alex Morgan
Company Name: Example Solutions
Client Name: Example Retail
Project Name: Inventory Management
```

You launch only `Employee`, because that is where we placed `main`. The JVM can also load the supporting classes from the same `out` classpath root.

The execution flow is:

```text
Employee.main
    -> Create Employee object
    -> Create Company object
    -> Create Client object
    -> Create Project object
    -> Call employee.empName()
    -> Call company.companyName()
    -> Call client.clientName()
    -> Call project.projectName()
    -> Finish
```

After editing a source file, save it and compile again before launching from the terminal.

## 8. Add a README

Create `README.md`:

````markdown
# EmployeeInformation

Core Java OOP — Question 03.

## Objective

Create objects of Employee, Company, Client, and Project inside
Employee.main, then call each object's required instance method.

## Structure

| Package | Class | Method |
|---|---|---|
| org.emp | Employee | empName() |
| org.company | Company | companyName() |
| org.client | Client | clientName() |
| org.project | Project | projectName() |

## Requirements

JDK 25. No external dependencies or build tools are required.

## Compile

From the project root:

```bash
javac --release 25 -d out \
    src/org/emp/Employee.java \
    src/org/company/Company.java \
    src/org/client/Client.java \
    src/org/project/Project.java
```

## Run

```bash
java -cp out org.emp.Employee
```

## Concepts practiced

- Packages and explicit imports
- Public classes and methods
- Object creation and instance-method calls
- Local-variable type inference with var
- Multi-file compilation and the classpath

All names and details are fictional demonstration data.
````

## 9. Commit and publish the repository

Check repository boundaries before initializing:

```bash
git rev-parse --show-toplevel
```

- **Not a git repository:** expected for a new independent project; initialize below.
- **The EmployeeInformation directory:** already initialized; skip initialization.
- **A parent directory:** stop and resolve the layout before creating a nested repository.

For a new repository:

```bash
git init -b main
```

Stage and review the intended files:

```bash
git add src/ .vscode/settings.json .gitignore README.md
git diff --cached --stat
git diff --cached
```

You should see four Java source files plus the README, settings, and ignore file. Generated bytecode and backups must not be included.

Commit:

```bash
git commit -m "Complete EmployeeInformation multi-package exercise"
```

Create an **empty** GitHub repository named `EmployeeInformation` under your account. Do not initialize it with another README or `.gitignore`.

If you create it under the same account as your previous repositories:

```bash
git remote add origin https://github.com/ub-victoire/EmployeeInformation.git
git push -u origin main
git status
```

Run `git remote add` only if this repository has no `origin` yet. If you use another account or repository name, copy its actual URL.

Finally, inspect the hosted repository in your browser and confirm that all four `.java` files are present.

## 10. Common mistakes

| Problem | Likely cause or correction |
|---|---|
| `package org.company does not exist` | Check the package declaration, source path, and that all four source files are included in compilation. |
| `cannot find symbol: class Company` | Check the import and capitalization of `Company`. |
| Class or method “is not public ... cannot be accessed from outside package” | Make the required class and method public. |
| `non-static method ... cannot be referenced from a static context` | Call the method through the corresponding object, such as `company.companyName()`. |
| Running `org.company.Company` fails because no main method exists | Launch `org.emp.Employee`; supporting classes do not need entry points. |
| Only three objects are created | Add the `Employee` object too. The instruction says all four classes. |
| Calls appear directly in the class body | Put the executable object-creation and call statements inside `main`. |
| Output contains only an employee name | Check that all four calls are present, then save and recompile. |
| File/class mismatch | Use `Employee.java`, `Company.java`, `Client.java`, and `Project.java`, matching case exactly. |

## 11. Final checklist

- [ ] Separate project/repository named `EmployeeInformation`.
- [ ] Four correctly named packages and public classes.
- [ ] One required public instance method in each class.
- [ ] `Employee` imports the other three classes.
- [ ] `Employee.main` explicitly creates all four objects.
- [ ] Each object's method is called once.
- [ ] Compilation succeeds and execution prints four lines.
- [ ] Generated files stay outside the committed source.
- [ ] README contains working compile and run commands.
- [ ] All intended files are pushed to the remote repository.

## 12. Check your understanding

1. Does importing `Company` create a company object? Why not?
2. Why must `Company` and `companyName()` both be public here?
3. Why does `Employee` not need `import org.emp.Employee;`?
4. Why is only one `main` method necessary for this program?
5. What would happen if `company.companyName()` were removed but the object creation remained?

**Next checkpoint:** share your four source files and the compile/run output. Output can demonstrate behavior; reviewing the source is what confirms that the required packages and object creations are implemented correctly.
