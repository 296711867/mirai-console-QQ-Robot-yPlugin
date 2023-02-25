plugins {
    val kotlinVersion = "1.5.10"
    val miraiVersion = "2.7.1"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion

    id("net.mamoe.mirai-console") version miraiVersion


}
group = "yplugin"
version = "1.0.1"

repositories {
    maven("https://maven.aliyun.com/repository/public")
    mavenLocal()
    mavenCentral()
}


dependencies {
    implementation("org.json:json:20220924")

    implementation("org.quartz-scheduler:quartz:2.3.2")
    implementation("org.jsoup:jsoup:1.15.2")

    implementation("cn.hutool:hutool-all:5.8.5")
    implementation("com.alibaba:fastjson:1.1.15")

    compileOnly("org.projectlombok:lombok:1.18.24")
// https://mvnrepository.com/artifact/org.quartz-scheduler/quartz
//    implementation group: 'org.quartz-scheduler', name: 'quartz', version: '2.3.2'

}
