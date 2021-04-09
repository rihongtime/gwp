rootProject.name = "gwp"

include("management:api")
include("management:service")
include("management:core")

include("front:api")
include("front:service")
include("front:core")

include("persistence:common")
include("persistence:contents")
include("persistence:purchase")