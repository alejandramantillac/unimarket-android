# Documentación de Políticas de Acceso de UniMarket

> Fecha: 15 de mayo de 2025  
> Versión: 2.0

Este documento detalla las políticas de acceso definidas para cada tabla en el sistema, siguiendo el enfoque de dos roles principales: `USER` (compradores) y `ENTREPRENEUR` (vendedores), con énfasis en el manejo correcto de relaciones many-to-many.

## Definición de roles

- **PUBLIC**: Acceso sin autenticación
- **USER**: Usuario autenticado con rol 'Comprador' (11111111-1111-1111-1111-111111111111)
- **ENTREPRENEUR**: Usuario autenticado con rol 'Emprendedor' (22222222-2222-2222-2222-222222222222)

## Políticas por tabla

### Product
```
Product:
    PUBLIC: NONE
    USER:
        READ: [published=true] # all excepto (!stock_alert, !published)
    ENTREPRENEUR:
        READ: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [entrepreneurship.id IN (Partner relacionados al usuario actual)] OR 
              [entrepreneurship.id IN (CollaborationMembers relacionados)] # all fields
        CREATE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        DELETE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.id IN (Partner relacionados al usuario actual)]
```

### ProductVariant
```
ProductVariant:
    PUBLIC: NONE
    USER:
        READ: [product.published=true] # fields: id, nombre, product (sin stock)
    ENTREPRENEUR:
        READ: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] OR
              [product.id IN (CollaborationProducts relacionados)] # all fields
        CREATE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        DELETE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)]
```

### ProductImage
```
ProductImage:
    PUBLIC: NONE
    USER:
        READ: [product_variant.product.published=true] # fields: id, image_url
    ENTREPRENEUR:
        READ: [product_variant.product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [product_variant.product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        CREATE: [product_variant.product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product_variant.product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [product_variant.product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product_variant.product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        DELETE: [product_variant.product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product_variant.product.entrepreneurship.id IN (Partner relacionados al usuario actual)]
```

### ProductSpecification
```
ProductSpecification:
    PUBLIC: NONE
    USER:
        READ: [product.published=true] # all fields
    ENTREPRENEUR:
        READ: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        CREATE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        DELETE: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [product.entrepreneurship.id IN (Partner relacionados al usuario actual)]
```

### Entrepreneurship
```
Entrepreneurship:
    PUBLIC: NONE
    USER:
        READ: [status = "active"] # fields: id, name, slogan, description, email, phone, status, category
    ENTREPRENEUR:
        READ: [user_founder.id = $CURRENT_USER] OR
              [id IN (Partner relacionados al usuario actual)] # all fields
        CREATE: [user_founder.id = $CURRENT_USER] # all fields
        UPDATE: [user_founder.id = $CURRENT_USER] OR
                [Partner._some: {user_profile: { _eq: $CURRENT_USER }, partner_rol: 1}] # all fields
        DELETE: [user_founder.id = $CURRENT_USER] # hard: solo dueño
```

### Partner (Tabla de relación many-to-many)
```
Partner:
    PUBLIC: NONE
    USER:
        READ: [entrepreneurship.status = "active"] # fields: id, user_profile, entrepreneurship, rating, partner_rol
    ENTREPRENEUR:
        READ: [entrepreneurship.user_founder.id = $CURRENT_USER] OR 
              [user_profile.id = $CURRENT_USER] # all fields
        CREATE: [entrepreneurship.user_founder.id = $CURRENT_USER] # all fields
        UPDATE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR 
                [user_profile.id = $CURRENT_USER] # campos restringidos según rol
        DELETE: [entrepreneurship.user_founder.id = $CURRENT_USER] # solo dueño
```

### EntrepreneurshipCustomization
```
EntrepreneurshipCustomization:
    PUBLIC: NONE
    USER:
        READ: [Entrepreneurship.status = "active"] # all fields
    ENTREPRENEUR:
        READ: [Entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [Entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        CREATE: [Entrepreneurship.user_founder.id = $CURRENT_USER] # all fields
        UPDATE: [Entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [Entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
```

### EntrepreneurshipSubscription
```
EntrepreneurshipSubscription:
    PUBLIC: NONE
    USER:
        READ: NONE
    ENTREPRENEUR:
        READ: [Entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [Entrepreneurship.Partner._some: {user_profile: { _eq: $CURRENT_USER }, partner_rol: 1}] # all fields
        UPDATE: [Entrepreneurship.user_founder.id = $CURRENT_USER] # all fields
```

### Order
```
Order:
    PUBLIC: NONE
    USER:
        READ: [user_profile.id = $CURRENT_USER] # all fields
        CREATE: [user_profile.id = $CURRENT_USER] # all fields
        UPDATE: [user_profile.id = $CURRENT_USER AND status = "Pending"] # campos limitados
    ENTREPRENEUR:
        READ: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.id IN (Partner relacionados al usuario actual)] # campos de estado
```

### OrderDetail
```
OrderDetail:
    PUBLIC: NONE
    USER:
        READ: [order.user_profile.id = $CURRENT_USER] # all fields
        CREATE: [order.user_profile.id = $CURRENT_USER AND order.status = "Pending"] # all fields
    ENTREPRENEUR:
        READ: [order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [order.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
```

### Payment
```
Payment:
    PUBLIC: NONE
    USER:
        READ: [order.user_profile.id = $CURRENT_USER] # all fields
        CREATE: [order.user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [order.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
        UPDATE: [order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [order.entrepreneurship.id IN (Partner relacionados al usuario actual)] # campos de estado
```

### PaymentEvidence
```
PaymentEvidence:
    PUBLIC: NONE
    USER:
        READ: [payment.order.user_profile.id = $CURRENT_USER] # all fields
        CREATE: [payment.order.user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [payment.order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [payment.order.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
```

### Delivery
```
Delivery:
    PUBLIC: NONE
    USER:
        READ: [order.user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [order.entrepreneurship.id IN (Partner relacionados al usuario actual)] OR
              [partner.user_profile.id = $CURRENT_USER] # all fields
        UPDATE: [order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [order.entrepreneurship.id IN (Partner relacionados al usuario actual)] OR
                [partner.user_profile.id = $CURRENT_USER] # campos específicos
```

### DeliveryStatusLog
```
DeliveryStatusLog:
    PUBLIC: NONE
    USER:
        READ: [delivery.order.user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [delivery.order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [delivery.order.entrepreneurship.id IN (Partner relacionados al usuario actual)] OR
              [delivery.partner.user_profile.id = $CURRENT_USER] # all fields
        CREATE: [delivery.order.entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [delivery.order.entrepreneurship.id IN (Partner relacionados al usuario actual)] OR
                [delivery.partner.user_profile.id = $CURRENT_USER] # all fields
```

### Review
```
Review:
    PUBLIC: NONE
    USER:
        READ: [product.published = true] # all fields
        CREATE: [user_profile.id = $CURRENT_USER AND producto comprado] # all fields
        UPDATE: [user_profile.id = $CURRENT_USER] # all fields
        DELETE: [user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [product.entrepreneurship.id IN (Partner relacionados al usuario actual)] # all fields
```

### Collaboration
```
Collaboration:
    PUBLIC: NONE
    USER:
        READ: NONE
    ENTREPRENEUR:
        READ: [CollaborationMembers._some: {
                entrepreneurship: {
                    _or: [
                        {user_founder: { _eq: $CURRENT_USER }},
                        {Partner._some: {user_profile: { _eq: $CURRENT_USER }}}
                    ]
                }
              }] # all fields
        CREATE: [cualquier emprendedor] # all fields
        UPDATE: [CollaborationMembers._some: {
                  entrepreneurship: {
                      _or: [
                          {user_founder: { _eq: $CURRENT_USER }},
                          {Partner._some: {user_profile: { _eq: $CURRENT_USER }}}
                      ]
                  }
                }] # all fields
```

### CollaborationMembers
```
CollaborationMembers:
    PUBLIC: NONE
    USER:
        READ: NONE
    ENTREPRENEUR:
        READ: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [entrepreneurship.Partner._some: {user_profile: { _eq: $CURRENT_USER }}] # all fields
        CREATE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.Partner._some: {user_profile: { _eq: $CURRENT_USER }, partner_rol: 1}] # all fields
        DELETE: [entrepreneurship.user_founder.id = $CURRENT_USER] OR
                [entrepreneurship.Partner._some: {user_profile: { _eq: $CURRENT_USER }, partner_rol: 1}] # all fields
```

### CollaborationProducts
```
CollaborationProducts:
    PUBLIC: NONE
    USER:
        READ: NONE
    ENTREPRENEUR:
        READ: [collaboration.CollaborationMembers._some: {
                entrepreneurship: {
                    _or: [
                        {user_founder: { _eq: $CURRENT_USER }},
                        {Partner._some: {user_profile: { _eq: $CURRENT_USER }}}
                    ]
                }
              }] OR
              [product.entrepreneurship.user_founder.id = $CURRENT_USER] OR
              [product.entrepreneurship.Partner._some: {user_profile: { _eq: $CURRENT_USER }}] # all fields
        CREATE: [producto propio Y miembro de colaboración] # all fields
        DELETE: [producto propio O owner de colaboración] # all fields
```

### user_profile
```
user_profile:
    PUBLIC: NONE
    USER:
        READ: [id = $CURRENT_USER] # campos públicos limitados
        UPDATE: [id = $CURRENT_USER] # campos específicos
    ENTREPRENEUR:
        READ: [id = $CURRENT_USER] # all fields
        UPDATE: [id = $CURRENT_USER] # all fields
```

### Notification
```
Notification:
    PUBLIC: NONE
    USER:
        READ: [user_profile.id = $CURRENT_USER] # all fields
    ENTREPRENEUR:
        READ: [user_profile.id = $CURRENT_USER] # all fields
```

## Notas importantes

1. La adición del campo `published` a los productos permite un mayor control sobre qué productos son visibles para los usuarios regulares.
2. Los usuarios tipo comprador (USER) solo pueden ver productos marcados como publicados (`published=true`).
3. Los campos sensibles como `stock_alert` y `published` solo son visibles para emprendedores asociados al producto.
4. Las relaciones many-to-many (como Partner y Entrepreneurship) se manejan usando operadores `_some` y filtrando a través de las tablas de unión.
5. Para los casos donde necesitamos filtrar por emprendimientos asociados al usuario actual a través de la tabla Partner, usamos la sintaxis correcta de relaciones many-to-many.
6. Cuando se requiere verificar si un usuario es partner de un emprendimiento, usamos `Partner._some: {user_profile: { _eq: $CURRENT_USER }}` en lugar de la relación incorrecta usada anteriormente.