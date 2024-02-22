CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE AddressEntity (
    id UUID PRIMARY KEY UNIQUE,
    userId UUID NULL
);

CREATE TABLE ProductVariantVersionEntity (
    id UUID PRIMARY KEY UNIQUE,
    weight DOUBLE PRECISION NOT NULL
);

CREATE TABLE OrderItemEntity (
    id UUID PRIMARY KEY UNIQUE
);

CREATE TABLE ShipmentMethodEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL,
    baseFees INT NOT NULL,
    feesPerItem INT NOT NULL,
    feesPerKg INT NOT NULL,
    archivedAt TIMESTAMPTZ NULL
);

CREATE TABLE ShipmentEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    status VARCHAR(255) NOT NULL,
    shipmentMethodId UUID NOT NULL,
    shipmentAddressId UUID NOT NULL,
    FOREIGN KEY (shipmentMethodId) REFERENCES ShipmentMethodEntity(id),
    FOREIGN KEY (shipmentAddressId) REFERENCES AddressEntity(id)
);

CREATE TABLE ShipmentToOrderItemEntity (
    id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
    shipmentId UUID NOT NULL,
    orderItemId UUID NOT NULL,
    FOREIGN KEY (shipmentId) REFERENCES ShipmentEntity(id),
    FOREIGN KEY (orderItemId) REFERENCES OrderItemEntity(id)
);
)
