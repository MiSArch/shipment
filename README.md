# Shipment Service

The shipment service provides the bounded context `Shipment`.
It is responsible for managing shipments and their state, and interacts with an external shipment provider to do so.

## Documentation

Detailed information about the shipment service can be found in the [documentation](https://misarch.github.io/docs/docs/dev-manuals/services/shipment).


## Getting started

A development version of the shipment service can be started using docker compose:

```bash
docker-compose -f docker-compose.dev.yml up --build
```
A GraphiQL interface is available at http://localhost:8080/graphiql to interact with the service.

> [!NOTE]
> Running the service locally through the IDE is neither recommended nor supported.