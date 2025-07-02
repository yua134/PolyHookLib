# PolyHookLib

> 🔗 日本語版は [README.ja.md](./README.ja.md) をご覧ください  
> 🔗 English version is [README.md](./README.md)

**PolyHookLib** は、Minecraftのバニラクラスに Mixin と Interface を適用することで、  
異なる型に共通のアクセス手段を提供する Forge 1.20.1 向けライブラリMODです。

本MODは、**異なるクラスに共通のインターフェースを注入**し、  
開発者が `instanceof` とキャストによって動的に型を扱えるようにすることを目的としています。



## 📦 機能概要

| 対象クラス | 追加インターフェース | 提供されるメソッド |
|------------|----------------------|--------------------|
| `Block` | `IBlockAccess` | `getAsBlock()` |
| `BlockEntity` | `IBlockEntityAccess` | `getAsBlockEntity()` |
| `Entity` | `IEntityAccess` | `getAsEntity()` |
| `Item` | `IItemAccess` | `getAsItem()` |
| `Level` | `ILevelAccess` | `getAsLevel()` |
| `Player` | `IPlayerAccess` | `getAsPlayer()` |
| *(注: `ItemStack` は現在非対応)* | `IItemStackAccess` | - |

> 現在のバージョンでは、基本的な `getAsX()` のみ提供しています。  
> より高度なAPIは今後のバージョンで拡張予定です。




## 🔧 利用方法（開発者向け）

1. `build.gradle` などに依存を追加（今後Maven等対応予定）
2. 対象オブジェクトに対して `instanceof I〇〇Access` で判定
3. キャスト後 `getAs〇〇()` を呼び出すことで本来の型を取得可能

例：

```java
if (object instanceof IBlockAccess access) {
    Block b = access.getAsBlock();
    // Block に対する操作...
}
````



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

