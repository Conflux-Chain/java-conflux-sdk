# How to publish

1. Update version in build.gradle
2. 使用 gradle publish 发布到 OSSRH
3. 到 oss.sonatype.org 的 Staging repositories 把将要发布的版本 close 掉
4. 待状态变为 closed 后，进行 Release 操作

## Configure

1. 在发布新版本 artifact 到 maven 之前, 需要对他们进行 sign, 签名需要用到 signingPassword 和 signingKey(pgp key); (PS 忘记如何生成了🤣)
2. 在发布到 maven 的时候, 需要通过 `oss.sonatype.org` 生成的 accessToken 来登录, 之前是 userName 和 password, 2024.6 后不再支持