# PolyHookLib

> 🔗 日本語版は [README.ja.md](./README.ja.md) をご覧ください

**PolyHookLib** is a Forge 1.20.1 library mod that provides a unified way to access different Minecraft vanilla classes
by injecting **Mixin-based interfaces** into them.

This mod allows developers to **dynamically treat multiple unrelated types in a unified way**,
by injecting shared interfaces into otherwise unrelated classes, and using `instanceof` and casting to interact with them.


## 📦 Features

| Target Class                                   | Injected Interface   | Provided Method      |
| ---------------------------------------------- | -------------------- | -------------------- |
| `Block`                                        | `IBlockAccess`       | `getAsBlock()`       |
| `BlockEntity`                                  | `IBlockEntityAccess` | `getAsBlockEntity()` |
| `Entity`                                       | `IEntityAccess`      | `getAsEntity()`      |
| `Item`                                         | `IItemAccess`        | `getAsItem()`        |
| `Level`                                        | `ILevelAccess`       | `getAsLevel()`       |
| `Player`                                       | `IPlayerAccess`      | `getAsPlayer()`      |
| *(Note: `ItemStack` is currently unsupported)* | `IItemStackAccess`   | -                    |

> The current version only provides basic `getAsX()` methods.
> More advanced APIs may be added in future versions.


## 🔧 Usage (For Developers)

### ✅ Basic Usage: Accessing Types via `IAccess` Interfaces

This library injects common `IAccess` interfaces into vanilla classes using Mixins,
allowing you to treat different types in a unified and polymorphic way.

#### 📌 Basic Example

```java
if (object instanceof IBlockAccess access) {
    Block block = access.getAsBlock();
    // Do something with the block
}
```

#### 🛠 For Safe Access, Use `AccessHelper` (Recommended)

```java
AccessHelper.tryAsPlayer(entity).ifPresent(player -> {
    // Safe access using Optional<Player>
});
```

`AccessHelper` provides `tryAsXxx(T)` utility methods for each `IAccess` interface,
allowing null-safe retrieval via `Optional`.

| Target Class                            | Injected Interface   | Provided Method      |
| --------------------------------------- | -------------------- | -------------------- |
| `Block`                                 | `IBlockAccess`       | `getAsBlock()`       |
| `BlockEntity`                           | `IBlockEntityAccess` | `getAsBlockEntity()` |
| `Entity`                                | `IEntityAccess`      | `getAsEntity()`      |
| `Item`                                  | `IItemAccess`        | `getAsItem()`        |
| `Level`                                 | `ILevelAccess`       | `getAsLevel()`       |
| `Player`                                | `IPlayerAccess`      | `getAsPlayer()`      |
| *(Note: `ItemStack` not yet supported)* | `IItemStackAccess`   | -                    |

---

### 🧩 Advanced Usage: Adding Behavior via Trait System

#### 💡 Overview

By using `Trait<T>` and `TraitFunction<T, R>`, you can dynamically add
**custom behaviors or logic** to specific classes from the outside and invoke them at runtime.
Each trait is uniquely identified using a `TraitID` in the format `modid:trait_name`.

---

### 🏗 How to Register (During Mod Initialization)

```java
String id = "my_trait";
Trait<MyType> func = (input) -> {
    // Custom logic
};

TraitRegistry.register(id, MyType.class, func);
```

```java
String id = "my_trait";
TraitFunction<MyType, MyResult> func = (input) -> {
    // Logic with return value
    return ...;
};

TraitFunctionRegistry.register(id, MyType.class, func);
```

* You may use any string as the trait ID.
  `Trait` and `TraitFunction` can share the same ID independently.
  However, within each registry, duplicate IDs are not allowed (unless modid is different).
* `register()` must be called **before** `FMLLoadCompleteEvent`.
  After that, the registry is locked and any further attempts will throw an exception.

---

### ▶ How to Use (Also Available at Runtime)

```java
Optional<MyResult> result = TraitHelper.applyFunction("yourmod:my_trait", MyType.class, instance);
```

* Traits are retrieved using a **fullId**, which is `modid + id used during registration`.
* `tryApplyFunction(...)`: Retrieves and executes a `TraitFunction`, returns `Optional<R>`.
* `tryApplyTrait(...)`: Executes a `Trait` without return value, returns `true` on success.
* `getTrait(...)`: Returns the trait itself as `Optional<Trait<T>>`.
* `getFunction(...)`: Retrieves the registered `TraitFunction` itself.

---

### 🚀 Expert Usage

PolyHookLib also allows you to **inject Trait or TraitFunction into external classes via Mixin**.
This enables **adding runtime behavior to classes defined by other mods**.

#### 🧪 Example: Injecting `Trait<MyType>` into Another Mod’s Class

1. Create a Mixin in your mod targeting the class you want to modify, and implement `Trait<MyType>`.
2. Register behavior via `TraitFunctionRegistry.register(...)`.
3. Call `TraitHelper.applyTrait(...)` during runtime.

> This approach showcases the true power of Mixins—injecting functionality into external codebases dynamically.

#### ⚠ Caution

* Be cautious when selecting target classes, as Mixin collisions may occur.
* Use `@Implements` or `@Unique` instead of `@Inject` to avoid interference as much as possible.
* These techniques are **recommended for advanced users only**.
  Improper implementation may cause crashes or mod conflicts.

---

### 🧾 Summary

| Feature         | Description                                      |
| --------------- | ------------------------------------------------ |
| `IAccess`       | Access the original type safely via `getAsXxx()` |
| `AccessHelper`  | Null-safe access using `Optional<Xxx>`           |
| `TraitID`       | Unique identifier in `modid:trait_name` format   |
| `TraitFunction` | Bind custom behavior as a lambda function        |
| `applyFunction` | Execute a registered `TraitFunction`             |
| `applyTrait`    | Execute a registered `Trait` for side effects    |

---


## ⚠ Warnings

* This mod uses **Mixin to modify base classes**, which may lead to **mod conflicts**.
* `final` classes (like `ItemStack`) **cannot be mixed into** and are thus unsupported (due to Java limitations).
* If another mod is also applying a Mixin to the same target class, **crashes may occur**.
* This mod is **intended for developers only** — players do not need to install it.

### 🚨 Regarding Use by Beginners

This mod is intended for **experienced developers** who understand the inner workings of Java and Minecraft.

> By using this mod, you can **disguise arguments to appear as common interfaces**,
> even when the original classes are unrelated.
> While powerful, this may also introduce **unexpected crashes, bugs, or security risks** if misused.

### ❗ You should **not use this mod** if:

* You are not familiar with Java inheritance, interfaces, or casting
* You do not understand how `instanceof` and `Mixin` work
* You lack knowledge of Minecraft/Forge internal class structures
* You plan to use it with a “just works” mindset

## 📝 License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## 🚧 Development Status

This project is currently in **early development**.
**Formal release is planned for a future date**, once stability and API maturity are ensured.

## 🚧 Roadmap

* Add more advanced API features

## 🗨 Feedback and Issues

For bug reports or suggestions, please use [GitHub Issues](https://github.com/yua134/polyhooklib/issues).
