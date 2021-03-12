import ProjectDescription

/// Project helpers are functions that simplify the way you define your project.
/// Share code to create targets, settings, dependencies, etc.
/// Docs: https://tuist.io/docs/usage/helpers/

public func bundleId(_ name: String) -> String {
    return "io.tuist.MyApp" + name
}

extension Settings {
    public static func standard() -> Settings {
        Settings(base: baseSettings().merging([
            "IPHONEOS_DEPLOYMENT_TARGET": "10.0"
        ]))
    }

    public static func test() -> Settings {
        Settings(base: baseSettings().merging([
            "IPHONEOS_DEPLOYMENT_TARGET": "11.0"
        ]))
    }
}

private func baseSettings() -> SettingsDictionary {
    [
        "SDKROOT": "iphoneos",
        "SWIFT_VERSION": "5.3"
    ]
}
