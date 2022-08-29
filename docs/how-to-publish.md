# How to publish

1. Update version in build.gradle
2. 使用 gradle publish 发布到 OSSRH
3. 到 oss.sonatype.org 的 Staging repositories 把将要发布的版本 close 掉
4. 待状态变为 closed 后，进行 Release 操作