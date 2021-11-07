Public Holidays API
===========

A web API for getting public holidays. The app is powered by Spring Boot 2.

## API Summary

With [HAL Explorer](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#tools.hal-explorer) you can browse and explore HAL based RESTful Hypermedia API locally: `http://localhost:8090/explorer/index.html#uri=/`

`/countries` - Gives you a list of supported countries

`/countries/AU/provinces` - Gives you a list of Australian provinces

`/countries/AU/holidays` - Gives you a list of Australian holidays

`/countries/AU/holidays?province_id=ACT` - Gives you a list of holidays in the specified Australian province

`/countries/AU/2017-01-01` - Gives you info about a day in Australian calendar

`/countries/AU/2017-01-01/10` - Gives you a list of Australian holidays starting from date 2017-01-01 till date 2017-01-11 (a range of 10 days)

`/copyright` - Gives you a list of data sources and licenses

`/actuator/health` - Shows application health information (for monitoring)

## Supported Countries

| code | name |
| :--- | :--- |
| AU | Australia |
| HK | Hong Kong |
| NZ | New Zealand |
| SG | Singapore |

Currently, database contains holidays for 2017 and 2018 years only.

## Build & Run

### Build Using Gradle

Run `gradlew build` to build the project in production mode. The build artifacts will be stored in the `build/libs/` directory.

### Run Using Docker

The `/docker/docker-compose.yaml` file describes the configuration of service components. The app can be run in a local environment by going into the `/docker` directory and executing:

```shell
docker-compose up -d
```

To stop and remove all containers of the sample application run:

```shell
docker-compose down
```

### Test Using Gradle

Run `gradlew test` to run the test suite.

## Contributing

See [the contributing guide](CONTRIBUTING.md) for detailed instructions on how to get started.
