INSERT INTO ECommerce.Perfil (id, nombre) VALUES ('1', 'Administrador');
INSERT INTO ECommerce.Perfil (id, nombre) VALUES ('2', 'Almacén');
INSERT INTO ECommerce.Perfil (id, nombre) VALUES ('3', 'Cliente');

INSERT INTO ECommerce.Estado (id, nombre) VALUES ('1', 'Finalizada');
INSERT INTO ECommerce.Estado (id, nombre) VALUES ('2', 'Pendiente');

INSERT INTO ECommerce.Usuario (correo, nombre, ap_paterno, ap_materno, password, Perfilid) VALUES ('admin@ecommerce.com', 'Sergio', 'Ramírez', 'Camacho', 'admin', '1');

