import ProjectDescriptionHelpers
import ProjectDescription

// you have to declare variables before actually using them
let sources = "Sources/**"

let project = Project(
    name: "Beagle",
    organizationName: "ZupIt",
    packages: [
        .remote(url: "https://github.com/pointfreeco/swift-snapshot-testing", requirement: .exact("1.7.2"))
    ],
    targets: [
        mainTarget(),
        testTarget()
    ],
    additionalFiles: [
        "**/__Snapshots__/**/*.png"
    ]
)

private func mainTarget() -> Target {
    Target(
        name: "Beagle",
        platform: .iOS,
        product: .framework,
        bundleId: bundleId("Beagle"),
        infoPlist: .file(path: "Resources/Info.plist"),
        sources: .init(globs: [
            "CodeGeneration/**/*.swift",
            SourceFileGlob("\(sources)/*.swift", excluding: [
                "\(sources)/**/Test/**/*.swift",
                "\(sources)/**/Tests/**/*.swift",
                "\(sources)/**/*Test*.swift"
            ])
        ]),
        resources: ["CodeGeneration/Templates/*"],
        actions: [
            .sourcery(),
//            .swiftLint()
        ],
        dependencies: [
            .project(target: "YogaKit", path: "../../Libraries/YogaKit"),
        ],
        settings: .standard()
    )
}

private func testTarget() -> Target {
    let name = "BeagleTests"

    return Target(
        name: name,
        platform: .iOS,
        product: .unitTests,
        bundleId: bundleId(name),
        infoPlist: .file(path: "BeagleTests/Info.plist"),
        sources: [
            "BeagleTests/**/*.swift",
            "\(sources)/**/Test/**/*.swift",
            "\(sources)/**/Tests/**/*.swift",
            "\(sources)/**/*Test*.swift"
        ],
        resources: .init(resources: [
            "../../../common/tests/UrlBuilderTestSpec.json",
            "\(sources)/**/Test/**/*.json",
            "\(sources)/**/Tests/**/*.json",
            "\(sources)/**/*Test*.json",
            "\(sources)/**/Test/**/*.xcassets",
            "\(sources)/**/Tests/**/*.xcassets",
            "\(sources)/**/*Test*.xcassets"
        ]),
        actions: [
//             .swiftLint()
        ],
        dependencies: [
            .target(name: "Beagle"),
            .project(target: "YogaKit", path: "../../Libraries/YogaKit"),
            .package(product: "SnapshotTesting")
        ],
        settings: .test()
    )
}
