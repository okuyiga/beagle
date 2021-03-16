import ProjectDescription

let config = Config(
    cache: .cache(profiles: [Cache.Profile.profile(name: "Default", configuration: "Release")]),
    generationOptions: [.enableCodeCoverage]
)
