# 🧠 Memory Bottle · 记忆时光瓶

一个记录与珍藏回忆的平台，支持图文上传、时间轴展示、留言互动等功能。

## ✨ 项目简介

Memory Bottle 提供了结构清晰、权限可控的回忆存档系统，支持用户上传图文回忆、时间筛选与分页查看、回忆评论、自动生成时间轴等核心功能，适用于家庭记忆留存、亲情互动等应用场景。

## ⚙️ 技术栈

- Java 17
- Spring Boot 3.x
- Spring Data JPA + MySQL
- RESTful API 设计
- Multipart 文件上传
- 前后端分离架构

## 📁 项目结构

```
memory-bottle/
├── controller 控制器层：对外接口
├── service 服务层：业务逻辑处理
├── repository 持久层：数据库访问
├── domain 实体类：Memory、TimelineEvent 等
├── dto / vo 数据传输对象 & 展示视图对象
└── utils 工具类：如文件上传处理
```

## 🚀 核心功能

- ✅ 上传回忆（含媒体文件）
- ✅ 分页 + 关键字搜索
- ✅ 按时间范围筛选
- ✅ 时间轴事件展示（附封面图）
- ✅ 评论功能（添加 / 查看 / 删除）
- ✅ 接口身份识别与权限控制

## 🧩 接口概览

| 方法  | 路径                          | 功能                     |
|-------|-------------------------------|--------------------------|
| POST  | `/memories/upload`           | 上传回忆（含文件）        |
| GET   | `/memories`                  | 获取回忆列表（含分页搜索） |
| GET   | `/memories/{id}`             | 查看回忆详情              |
| PUT   | `/memories/{id}`             | 更新回忆内容              |
| DELETE| `/memories/{id}`             | 删除回忆                  |
| POST  | `/comments`                  | 添加评论                  |
| GET   | `/comments/{memoryId}`       | 获取评论列表              |
| DELETE| `/comments/{commentId}`      | 删除评论                  |
| GET   | `/timeline`                  | 获取全局时间轴            |

📄 完整接口文档请见：[后端接口文档](./docs/记忆时光瓶_最终后端接口文档.md)

## 🔐 权限与身份识别

- 通过请求头 `X-User-Id` 注入当前用户身份
- 核心操作接口需验证权限，服务端使用 `checkPermission(userId, ownerId)` 校验资源归属
- 返回结构统一封装为 `Result<T>`，包含状态码、信息与数据内容

## 📂 媒体与文件说明

- 支持上传图片与视频文件
- 自动识别媒体类型（`IMAGE` / `VIDEO`）
- 媒体文件保存在本地 `/uploads/media/`
- 时间轴封面自动优先显示图片，若无则取视频

## 📦 运行环境

- JDK 17+
- Maven 3.8+
- 本地或远程 MySQL 实例
- 建议通过 Postman 或 Swagger 调试接口

---

欢迎提交 Issue 或 Pull Request 进行交流与改进。
