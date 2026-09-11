# creating-your-first-grails-app

Sample app for **Creating your first Grails Application** (Apache Grails `8.0.0-SNAPSHOT`, JDK 21).

This is the foundational onboarding guide: domain classes, controllers, GSP views, services, scaffolding, and Spock tests. Work through it if you are new to Grails or want a refresher on the basics.

## Layout

| Directory | What it is |
|-----------|------------|
| `initial/` | Vanilla Grails 8 web starter from [start.grails.org](https://start.grails.org) (`web` profile, GSP, H2, scaffolding, Spock, Geb). Start here and follow the guide. |
| `complete/` | Finished sample — `Make` / `Model` / `Vehicle` domains, GORM data services, scaffolded Make/Model controllers, a custom `VehicleController` with GSPs, `ValueEstimateService`, a home page, and unit + Geb integration specs. |

## Running

```bash
git clone -b grails8 https://github.com/grails-guides/creating-your-first-grails-app.git
cd creating-your-first-grails-app/complete
./gradlew test integrationTest
```

To follow the guide step by step, start from `initial/`:

```bash
cd creating-your-first-grails-app/initial
./gradlew test
```

Run the finished app:

```bash
cd creating-your-first-grails-app/complete
./gradlew bootRun
```

Then open http://localhost:8080/ — you should see the home page with a few seeded vehicles. Try `/vehicle/index`, `/make/index`, and `/model/index` as well.

## Requirements

- **JDK 21** (Temurin recommended; the Gradle build enforces Java 21+)
- **Docker** — needed for `integrationTest` (Geb + Testcontainers). Unit tests (`./gradlew test`) do not need Docker.

If Gradle reports *"Run this build using a Java 21 or newer JVM"*:

```bash
sdk install java 21.0.6-tem
sdk default java 21.0.6-tem
java -version   # should show 21.x
```

If `integrationTest` fails with a Testcontainers / `docker.sock` error, start Docker Desktop (or your local Docker daemon) and retry.

## What the sample covers

```groovy
// Domains with a simple association
class Model {
    String name
    static belongsTo = [make: Make]
}

// GORM data services for BootStrap seeding
@Service(Vehicle)
interface VehicleService {
    Vehicle save(String name, Make make, Model model, Integer year)
}

// Scaffolded CRUD for Make / Model
@Scaffold(Make)
class MakeController {
}

// Custom controller + service on the Vehicle show page
def show(Vehicle vehicle) {
    respond vehicle, model: [estimatedValue: valueEstimateService.getEstimate(vehicle)]
}
```

Controllers stay thin; GSPs under `grails-app/views/` render the pages (`<f:all>`, `<f:display>`, `<f:table>` from Fields/scaffolding).

## Guide prose

Published narrative lives on [grails.apache.org/guides](https://grails.apache.org/guides/) in [apache/grails-static-website](https://github.com/apache/grails-static-website) under `guides/creating-your-first-grails-app/v8/`.

## CI

GitHub Actions (`.github/workflows/grails8.yml`) runs `./gradlew test` for `initial` and `complete`, and `./gradlew integrationTest` for `complete`, on pushes and PRs to the `grails8` branch.
