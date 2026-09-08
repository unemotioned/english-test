# Compile

Compile and run without build tools.

---

## Compilation

- `find src -name "*.java"`: find all Java files
- `-d out`: compiled `.class` files into `out/` directory

```sh
javac -d out $(find src -name "*.java")
```

## Run

- `App`: Java file with `psvm`
- `-cp`: classpath points to out which runs the compiled files

```sh
java -cp out src.main.java.com.unemotioned.englishtest.App
```

### One Liner

```sh
javac -d out $(find src -name "*.java") && java -cp out src.main.java.com.unemotioned.englishtest.App
```

---

## JAR

After [compile](#compile).

Creates `.jar` file at **bin** directory:

```sh
jar --create \
    --file bin/english-test.jar \
    --main-class src.main.java.com.unemotioned.englishtest.App \
    -C out .
```

Run created `.jar` file:

```sh
java -jar bin/english-test.jar
```
