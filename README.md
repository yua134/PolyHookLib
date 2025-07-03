# PolyHookLib

> 🔗 日本語版は [README.ja.md](./README.ja.md) をご覧ください  
> 🔗 English version is [README.md](./README.md)

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

1. Add this mod as a dependency in your `build.gradle` (Maven or other repository support is planned).
2. Use `instanceof IXXXAccess` to check the target.
3. Cast and call `getAsXXX()` to get the original type.

Example:

```java
if (object instanceof IBlockAccess access) {
    Block block = access.getAsBlock();
    // Do something with the Block...
}
```

> ⚠ This syntax requires **Java 16 or later** (pattern matching for `instanceof`).
> For Java 8 or earlier, use explicit casting after checking with `instanceof`.

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
