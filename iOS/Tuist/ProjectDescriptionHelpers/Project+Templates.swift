import ProjectDescription

/// Project helpers are functions that simplify the way you define your project.
/// Share code to create targets, settings, dependencies, etc.
/// Docs: https://tuist.io/docs/usage/helpers/

public func bundleId(_ name: String) -> String {
    return "io.tuist.MyApp" + name
}

extension Settings {
    public static func standard(with additional: SettingsDictionary? = nil) -> Settings {
        Settings(base: baseSettings().merging([
            "IPHONEOS_DEPLOYMENT_TARGET": "10.0",
            "BUILD_LIBRARY_FOR_DISTRIBUTION": "YES"
        ]).merging(additional ?? [:]))
    }

    public static func test(with additional: SettingsDictionary? = nil) -> Settings {
        Settings(base: baseSettings().merging([
            "IPHONEOS_DEPLOYMENT_TARGET": "11.0"
        ]).merging(additional ?? [:]))
    }
}

private func baseSettings() -> SettingsDictionary {
    [
        "SDKROOT": "iphoneos",
        "SWIFT_VERSION": "5.3",
        "SUPPORTS_MACCATALYST": "NO",
        "TARGETED_DEVICE_FAMILY": "1,2"
    ]
}


extension TargetAction {

    public static func sourcery() -> TargetAction {
        .pre(path: "CodeGeneration/sourceryScript.sh", arguments: [], name: "Sourcery")
    }

    public static func swiftLint() -> TargetAction {
        .post(tool: "swiftlint", arguments: [], name: "SwiftLint")
    }
}
