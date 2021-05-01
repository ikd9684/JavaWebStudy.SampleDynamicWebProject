JavaWebStudy.SampleDynamicWebProject
====

[Docker を使って、Tomcat + MariaDB でのアプリ開発学習のための環境構築](https://github.com/ikd9684/docker-tomcat-mariadb)を使って、EclipseでWebアプリケーションを開発する際のサンプルプロジェクトです。  


## 注意点
Eclipse + Tomcat Plugin を使って動かす場合と、Docker 上の Tomcat にデプロイして動かす場合とで、DBのホスト名が異なるので、DBのホスト名は環境変数を参照するようにしてください。  

Docker では以下のように設定済みです。  
`DB_HOST`=`db`

Eclipseでは以下のように環境変数の定義を追加してください。  
`DB_HOST`=`localhost`

![スクリーンショット 2021-05-02 0 19 21](https://user-images.githubusercontent.com/2688618/116786913-3bef5600-aadc-11eb-9152-b0ca33c3bcb5.png)
