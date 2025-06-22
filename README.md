# アプリケーション名
ショッピングサイト「Book Store」
Spring Boot + Thymeleaf + MyBatis で構築したECサイト風アプリです。

# 使用技術スタック
- Java 21
- Spring Boot 3.1.3
- MyBatis 3.0.4(アノテーションベース)
- Lombok 1.18.36
- Thymeleaf (Spring Bootに準拠)
- Spring (Security Spring Bootに準拠)
- Bootstrap 5.3.0
- H2 Database (Spring Bootに準拠)
- Gradle

# 起動方法(開発用)
1. プロジェクトをクローン
git clone https://github.com/yoshiosushi/book-store.git 
cd book-store
2. アプリ起動
gradlew.bat bootRun (Windowsの場合)
./gradlew bootRun (Mac/Linuxの場合)
3. ブラウザで http://localhost:8080 にアクセス

# 主な機能
- ユーザー登録／ログイン／ログアウト
- 商品一覧表示・詳細ページ
- カート機能
- 購入処理(擬似的)
- ユーザー情報の編集
