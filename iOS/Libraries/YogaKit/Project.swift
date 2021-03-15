import ProjectDescriptionHelpers
import ProjectDescription

let sources = "YogaKit/Sources/"
let allSources = "YogaKit/Sources/**"

let project = Project(
    name: "YogaKit",
    organizationName: "ZupIt",
//    settings: Settings(
//        base: SettingsDictionary()
//            .swiftVersion("5.0")
//        debug: Configuration(xcconfig: "xcconfigs/Project-Debug.xcconfig"),
//        release: Configuration.init(xcconfig: "xcconfigs/Project-Release.xcconfig")
//    ),
    targets: [
        Target(
            name: "YogaKit",
            platform: .iOS,
            product: .framework,
            bundleId: bundleId("YogaKit"),
            infoPlist: .file(path: "YogaKit/Resources/Info.plist"),
            sources: ["\(allSources)/*.{m,swift}", "\(allSources)/*.{c,cpp}"],
            headers: Headers(
                public: [
                    "\(sources)ObjC/{UIView+Yoga,YGLayout,YGLayout+Private}.h",
                    "\(sources)/Cpp/yoga/{YGValue,YGMacros,YGEnums,Yoga}.h",
                    "YogaKit/Resources/YogaKit.h"
                ],
                project: "\(sources)Cpp/**/{experiments,event,CompactValue,Yoga-internal,experiments-inl,Utils,YGConfig,log,Bitfield,YGNodePrint,YGFloatOptional,YGLayout,YGStyle,YGNode}.h"
            ),
            settings: .standard(with: [
                "CLANG_ENABLE_MODULES": "YES",
                "LD_RUNPATH_SEARCH_PATHS": ["$(inherited)", "@executable_path/Frameworks", "@loader_path/Frameworks"]
            ])
        )
    ]
)
