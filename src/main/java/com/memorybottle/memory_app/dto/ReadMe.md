# DTO层说明

## 使用原因
由于使用了VO层，扩展了Converter层，因此为了统一数据安全采用了DTO层

### 在不使用DTO和VO层情况下，系统既不安全（数据暴露给了前端），又会影响后续开发
### 最终决定引入DTO、VO、Convert三个数据处理层

## DTO层相关

### DTO层作为前端传参结构封装所有网页前端发来的的数据
当前主要服务于POST接口