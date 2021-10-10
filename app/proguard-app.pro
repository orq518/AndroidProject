# 忽略警告
#-ignorewarning

# 混淆保护自己项目的部分代码以及引用的第三方jar包
#-libraryjars libs/umeng-analytics-v5.2.4.jar

# 不混淆这些包下的字段名
-keepclassmembernames class com.fxkj.huabei.http.request.** {
    <fields>;
}
-keepclassmembernames class com.fxkj.huabei.http.response.** {
    <fields>;
}
-keepclassmembernames class com.fxkj.huabei.http.model.** {
    <fields>;
}

# 不混淆被 DebugLog 注解的方法信息
-keepclassmembernames class ** {
    @com.fxkj.huabei.aop.DebugLog <methods>;
}