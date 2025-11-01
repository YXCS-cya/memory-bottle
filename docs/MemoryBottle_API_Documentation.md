# Memory Bottle Project · Final Backend API Documentation
## 🧠 Memory Module
| Function                                             | Method     | Endpoint           | Parameters                                                              | Access Control       |
| ---------------------------------------------------- | ---------- | ------------------ | ----------------------------------------------------------------------- | -------------------- |
| Upload a new memory (with media)                     | **POST**   | `/memories/upload` | `title`, `description`, `eventDate`, `mediaList`<br>Header: `X-User-Id` | ✅ Authenticated user |
| Get all memories (pagination + search + date filter) | **GET**    | `/memories`        | `keyword`, `startDate`, `endDate`, `page`, `size`                       | ❌ Public             |
| Get memory details                                   | **GET**    | `/memories/{id}`   | —                                                                       | ❌ Public             |
| Update memory content                                | **PUT**    | `/memories/{id}`   | Optional: `description`, `mediaList`<br>Header: `X-User-Id`             | ✅ Author or admin    |
| Delete a memory                                      | **DELETE** | `/memories/{id}`   | Header: `X-User-Id`                                                     | ✅ Author or admin    |

## 🕰️ Timeline Module
| Function                                 | Method   | Endpoint        | Parameters                                     | Access Control                                                  |
| ---------------------------------------- | -------- | --------------- | ---------------------------------------------- | --------------------------------------------------------------- |
| Add timeline event (for development use) | **POST** | `/timeline/add` | `memoryId`, `eventDate`<br>Header: `X-User-Id` | ✅ Developer only                                                |
| Retrieve all timeline events             | **GET**  | `/timeline`     | —                                              | ❌ Public (returns `memoryId`, `title`, `eventDate`, `coverUrl`) |

## 💬 Comment Module
| Function                  | Method     | Endpoint                | Parameters                                   | Access Control       |
| ------------------------- | ---------- | ----------------------- | -------------------------------------------- | -------------------- |
| Add a comment             | **POST**   | `/comments`             | `memoryId`, `content`<br>Header: `X-User-Id` | ✅ Authenticated user |
| Get comments for a memory | **GET**    | `/comments/{memoryId}`  | —                                            | ❌ Public             |
| Delete a comment          | **DELETE** | `/comments/{commentId}` | Header: `X-User-Id`                          | ✅ Author or admin    |

---

## 🔐 Authentication & Identity

- User identity is passed through request header X-User-Id.
- All protected endpoints validate ownership using checkPermission(userId, ownerId) in the service layer.

- Unified response format Result<T>:
```json
{
  "code": 200,
  "message": "Success",
  "data": { ... }
}
```

## 🧩 Media & Cover Display

- All uploaded media files are handled by the utility class FileUploadUtil and saved locally to /uploads/media/.
- Media types are automatically recognized as either IMAGE or VIDEO.
- Timeline endpoints automatically attach a coverUrl, prioritizing images over videos.

## 🧱 Permission & Structural Conventions
| Module                  | Description                                                                                          |
| ----------------------- | ---------------------------------------------------------------------------------------------------- |
| **Identity Injection**  | All secured endpoints require `X-User-Id` header for user identification.                            |
| **Permission Checking** | `checkPermission(userId, ownerId)` ensures only resource owners or admins can modify/delete content. |
| **Unified Response**    | All APIs return a `Result<T>` object with `code`, `message`, and `data` fields.                      |
| **Media Management**    | `FileUploadUtil` standardizes upload paths, media type detection, and file storage.                  |
| **Timeline Covers**     | Each timeline entry automatically includes a generated cover image or video preview.                 |

> ✅ The backend is fully functional, supporting memory creation, media uploads, timeline generation, and interactive commenting.
> All modules have been tested using Apifox and Postman.
