# MyMarket Marketplace Backend.

## Tecnologías usadas:
    - Spring boot 3.3
    - Maven
    - Java 17
## Librerías usadas:
    - Spring Security
    - JWT (Json Web Token)
    - Spring data JPA
    - MySQL Connector
    - Lombok

#### Sistema de logs basado en triggers de DB. (Query en /script)

## TODO Pero no creo llegar:
    - Ver mis compras
    - Sistema comprar

### Detalles de endpoints

#### /category:
- /save:
  - POST
  - Recibe obj por body
  - Guardado, Validación si existe (Por nombre)
  - PÚBLICA
- /edit:
  - POST
  - Recibe obj por body
  - Edición, Validacion si id viene null, Validacion si existe la categoria
  - PÚBLICA
- /getAll:
  - GET
  - Sin params
  - Búsqueda, no necesita validaciones, trae las categorías ordenadas alfabéticamente.
  - PÚBLICA
- /deleteById:
  - POST
  - ?id={id}
  - Borrado, Validación por si el id es null.
  - PÚBLICA
### /auth:
- /register:
  - POST
  - Recibe obj por body
  - Guardado, validación por si existe (Email, username y dni), password hasheada, devuelve jwt
  - PÚBLICA
- /login:
  - POST
  - Recibe obj por body
  - Validacion, comparación de password y la password hasheada de la db, devuelve jwt
  - PÚBLICA
### /user:
- /userExists:
  - GET
  - ?email={email}
  - Validacion, valida si existe EMAIL
  - PÚBLICA
- /recoverPassword:
  - POST
  - ?email={email}&newPassword={newPassword}
  - Recuperación, pide email y nueva password, valida si existe email
  - PÚBLICA
- /changePassword:
  - POST
  - ?email={email}&oldPassword={passVieja}}&newPassword={passNueva}
  - Validacion, si existe email, si la contraseña anterior coincide con la de la db y recién ahí cambia por la pass nueva.
  - PRIVADA
- /edit:
  - POST
  - Recibe obj por body
  - Validación, password y role no pueden cambiarse, valores vacíos lo deja por defecto. Devuelve JWT
  - PRIVADA
- /deleteByUsername:
  - POST
  - ?username={username}
  - Validacion por si existe usuario, y si el usuario posee productos, éstos se borran antes de que se elimine el usuario, el servicio es transaccional, si ocurre un error rollbackea.
  - PRIVADA
- /getByUsername:
  - GET
  - ?username={username}
  - mediante el usuario, trae todo el objeto usuario (También devuelve props de spring security. El backend para manejar el user no debe recibirlos nuevamente.)
  - PÚBLICA
### /product

- /save:
  - POST
  - Recibe obj por formData, 'product' JSONStringify, 'images' File[]
  - Guardado, valida por si existen categorías, guarda el producto sin el imagePath, seguido genera un directorio en el servidor para guardar las imágenes y vuelve a guardar el obj Producto con el stringPath actualizado, para ubicar las imágenes. Se guardan en /static/images/products/{idProducto}/{imágenes}
  - PRIVADA
- /getAllByPage:
  - GET
  - ?pageNo={numeroPágina}&itemsPage={itemsPorPágina}&opt={ordenamiento}
  - Búsqueda, No necesita validaciones. Devuelve paginado todos los productos con una única imágen por producto (si posee). Adicional al array de productos devuelve un obj 'pagingInfo' para poder sacar datos necesarios de la paginación. Se envia en 'opt' el tipo de ordenamiento, puede ser "NAME", "PRICE", "DESCRIPTION" o "CATEGORY".
  - PÚBLICA
- /getAllByName:
  - GET
  - ?pageNo={numeroPágina}&itemsPage={itemsPorPágina}&opt={ordenamiento}&searchReq={peticiónBúsqueda}
  - Búsqueda por nombre, No necesita validaciones. Devuelve paginado todos los productos con una única imágen por producto (si posee). Adicional al array de productos devuelve un obj 'pagingInfo' para poder sacar datos necesarios de la paginación. Se envia en 'opt' el tipo de ordenamiento, puede ser "NAME", "PRICE", "DESCRIPTION" o "CATEGORY".
  - PÚBLICA
- /edit:
  - POST
  - Recibe obj por formData, 'product' JSONStringify, 'images' File[]
  - Edición, el seller no cambia, isSold no cambia, buyer tampoco cambia. siempre tiene que recibir las imágenes el backend. Debido a que el service elimina todas las imágenes y carga las nuevas
  - PRIVADA
- /deleteById:
  - POST
  - ?id={idProducto}
  - Borrado, validacion por si el id es null. borra imágenes del servidor también.
  - PRIVADA
- /findById:
  - GET
  - ?id={id}
  - Búsqueda, validación por si no encuentra. Trae el producto completo con todas sus imágenes en base64.
  - PÚBLICA
- /findAllByUser:
  - GET
  - ?id={idUsuario}&pageNo={númeroPágina}&itemsPage={itemsPorPágina}
  - Busqueda, validación por si existe usuario, trae todos los productos que está vendiendo el usuario, con paginación. no devuelve imágen
  - PRIVADA
- /getRandomProducts:
  - POST
  - ?products={cantidadProductos}
  - Busqueda, devuelve productos de la base de datos con parámetros de seleccion aleatorios, trae cantidad de productos especificados en el query param, y una única imagen en B64 por producto
  - PÚBLICA
- /getImagesFileById
  - GET
  - ?id={idProducto}
  - trae todas las imágenes del producto en un DTO que trae la data del archivo, el nombre del archivo y el tipo de archivo
  - PÚBLICA


