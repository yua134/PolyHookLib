# PolyHookLib

> 🔗 English version is [README.md](./README.md)

**PolyHookLib** は、Minecraftのバニラクラスに Mixin と Interface を適用することで、  
異なる型に共通のアクセス手段を提供する Forge 1.20.1 向けライブラリMODです。

本MODは、**異なるクラスに共通のインターフェースを注入**し、  
開発者が `instanceof` とキャストによって動的に型を扱えるようにすることを目的としています。



## 🔧 使用方法（開発者向け）

### ✅ 基本的な使い方：`IAccess` インターフェースによる型アクセス

このライブラリは、バニラクラスに共通インターフェース（`IAccess` 系）を注入することで、異なる型を統一的に扱えるようにします。

#### 📌 基本構文

```java
if (object instanceof IBlockAccess access) {
    Block block = access.getAsBlock();
    // Block に対する処理
}
```


#### 🛠 安全に取得したいときは `AccessHelper` を使う（推奨）

```java
AccessHelper.tryAsPlayer(entity).ifPresent(player -> {
    // Optional<Player> で安全に取得・利用
});
```
AccessHelper は、各 IAccess の `tryAsXxx(T)` メソッドを提供するユーティリティクラスで、  
Optional を使って null セーフな取得を行うことができます。

| 対象クラス | 追加インターフェース | 提供されるメソッド      |
|------------|----------------------|----------------|
| `Block` | `IBlockAccess` | `getAsBlock()` |
| `BlockEntity` | `IBlockEntityAccess` | `getAsBlockEntity()` |
| `Entity` | `IEntityAccess` | `getAsEntity()` |
| `Item` | `IItemAccess` | `getAsItem()`  |
| `Level` | `ILevelAccess` | `getAsLevel()` |
| `Player` | `IPlayerAccess` | `getAsPlayer()` |
| *(注: `ItemStack` は現在非対応)* | `IItemStackAccess` | -              |


---

### 🧩 応用的な使い方：Trait システムによる振る舞いの追加

#### 💡 概要

`Trait<T>` や `TraitFunction<T, R>` を用いることで、**特定のクラスに任意の「振る舞い」や「処理」を外部から追加**し、実行時に動的に呼び出すことができます。
`modid:trait_name` 形式の `TraitID` を使って一意に識別されます。

---

### 🏗 登録方法（Mod 初期化時）
```java
String id = "my_trait";
Trait<MyType> func = (input) -> {
    //任意の処理
};

TraitRegistry.register(id, MyType.class, func);
```

```java
String id = "my_trait"; 
TraitFunction<MyType, MyResult> func = (input) -> {
    // 任意の処理
    return ...; //戻り値あり
};

TraitFunctionRegistry.register(id, MyType.class, func);
```


* IDは任意の文字列が使用できます。TraitとTraitFunctionにはそれぞれ同IDで登録できますが、Trait,TraitFunction内では同じIDで登録できません（ただし、modidが異なれば同じidで登録できます）。
* `register()` は `FMLLoadCompleteEvent` の前にのみ使用可能です（それ以降はロックされ例外になります）。

---

### ▶ 呼び出し方法（ゲーム実行中も可）

```java
Optional<MyResult> result = TraitHelper.applyFunction("yourmod:my_trait", MyType.class, instance);
```

* fullIdでTraitは取得します。fullIdは`modid+registry時のid`です
* `tryApplyFunction(...)`：TraitFunction を取得して実行。成功すれば `Optional<R>` を返します。
* `tryApplyTrait(...)`：戻り値が不要なときに使います（true/falseを返却）。
* `getTrait(...)`：Trait 自体を取得したいときに使います（Optional で返却）。
* `getFunctionTrait(...)`: TraitFunction自体を取得したいときに使います。

---

### 🚀 高度な使い方

PolyHookLib では、Mixin を用いて **外部クラスに Trait や TraitFunction を注入することも可能**です。
これにより、**他Modが定義するクラスに対しても「動的な処理の追加」が可能**になります。

#### 🧪 例：他のクラスに `Trait<MyType>` を注入

1. 自Mod内に Mixin を作成し、対象クラスに `Trait<MyType>` を inject
2. `TraitFunctionRegistry.register(...)` で動作を登録
3. 実行時に `TraitHelper.applyTrait(...)` で動作を呼び出し

> これはまさに「外部から機能を注入して使う」Mixin本来の力を生かした設計です。

#### ⚠ 注意点

* Mixinが衝突する可能性があるため、慎重に対象クラスを選びましょう。
* `@Inject` ではなく `@Implements` / `@Unique` を使うことで、干渉を最小限に抑える工夫が可能です。
* こうした手法は **中上級者向け** です。不適切な設計はクラッシュや競合の原因となります。

---

### 🧾 まとめ

| 項目              | 内容                             |
| --------------- | ------------------------------ |
| `IAccess` 系     | `getAsXxx()` で元の型に安全にアクセス      |
| `AccessHelper`  | `Optional<Xxx>` で null 安全なアクセス |
| `TraitID`       | `modid:trait_name` 形式で一意に識別    |
| `TraitFunction` | 任意の関数（ラムダ）を定義し、動作を動的に紐づける      |
| `applyFunction` | TraitFunction を呼び出して結果を得る      |
| `applyTrait`    | Trait を呼び出して副作用処理を行う           |

---




## ⚠️ 注意事項

* 本MODは**Mixinによるクラス改変**を行うため、**他MODとの競合**が発生する可能性があります。
* `ItemStack` など `final` クラスには対応できません（Javaの仕様によりMixinが無効）。
* 本MODを導入した状態で対象クラスに他のMODがMixinを行っていると、**クラッシュする可能性があります**。
* 本MODはあくまで**開発者向けのツール**です。通常のプレイヤーが導入する必要はありません。



### 🚨 初心者・学習目的での使用について

本MODは、**JavaやMinecraftの内部構造への深い理解を前提とした開発者向けライブラリ**です。

> このMODを使用すると、本来受け付けない型の引数を **あたかも共通の型であるかのように偽装して渡すことが可能**になります。
> これはとても強力な機能ですが、同時に**予期せぬクラッシュ・バグ・セキュリティリスク**の原因にもなります。



#### ❗ 次のような開発者には**使用を推奨しません**：

* Javaの継承やインターフェース、キャストの仕組みに不慣れな方
* `instanceof` や `Mixin` の動作を正しく理解していない方
* Forge や Minecraft のクラス構造を十分に把握していない方
* 「とりあえず動けばいい」程度の認識で使用する方



## 📝 ライセンス

MIT License を採用しています。詳細は `LICENSE` ファイルを参照してください。

## 🚧 開発状況

本プロジェクトは現在 **開発初期段階** にあります。
**安定性とAPI整備が完了した段階で正式リリースを予定**しています。

## 🚧 今後の予定

* 高度なAPIの追加


## 🗨 お問い合わせ・フィードバック

バグ報告・改善提案などは [GitHub Issues](https://github.com/yua134/polyhooklib/issues) へお願いします。

