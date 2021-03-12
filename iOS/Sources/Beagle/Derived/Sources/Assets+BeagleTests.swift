// swiftlint:disable all
// Generated using tuist — https://github.com/tuist/tuist

#if os(macOS)
  import AppKit
#elseif os(iOS)
  import UIKit
#elseif os(tvOS) || os(watchOS)
  import UIKit
#endif

// swiftlint:disable superfluous_disable_command file_length implicit_return

// MARK: - Asset Catalogs

// swiftlint:disable identifier_name line_length nesting type_body_length type_name
public enum BeagleTestsAsset {
  public enum ImageDownloaderTests {
    public static let beagle = BeagleTestsImages(name: "beagle")
  }
  public enum ImageTests {
    public static let keyboard = BeagleTestsImages(name: "keyboard")
    public static let shuttle = BeagleTestsImages(name: "shuttle")
    public static let testImageSquareX = BeagleTestsImages(name: "test_image_square-x")
  }
}
// swiftlint:enable identifier_name line_length nesting type_body_length type_name

// MARK: - Implementation Details

public struct BeagleTestsImages {
  public fileprivate(set) var name: String

  #if os(macOS)
  public typealias Image = NSImage
  #elseif os(iOS) || os(tvOS) || os(watchOS)
  public typealias Image = UIImage
  #endif

  public var image: Image {
    let bundle = BeagleTestsResources.bundle
    #if os(iOS) || os(tvOS)
    let image = Image(named: name, in: bundle, compatibleWith: nil)
    #elseif os(macOS)
    let image = bundle.image(forResource: NSImage.Name(name))
    #elseif os(watchOS)
    let image = Image(named: name)
    #endif
    guard let result = image else {
      fatalError("Unable to load image asset named \(name).")
    }
    return result
  }
}

public extension BeagleTestsImages.Image {
  @available(macOS, deprecated,
    message: "This initializer is unsafe on macOS, please use the BeagleTestsImages.image property")
  convenience init?(asset: BeagleTestsImages) {
    #if os(iOS) || os(tvOS)
    let bundle = BeagleTestsResources.bundle
    self.init(named: asset.name, in: bundle, compatibleWith: nil)
    #elseif os(macOS)
    self.init(named: NSImage.Name(asset.name))
    #elseif os(watchOS)
    self.init(named: asset.name)
    #endif
  }
}

