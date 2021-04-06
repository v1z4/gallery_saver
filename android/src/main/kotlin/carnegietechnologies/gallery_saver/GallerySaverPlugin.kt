package carnegietechnologies.gallery_saver

import android.app.Activity
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar

class GallerySaverPlugin  : FlutterPlugin, MethodCallHandler, ActivityAware {

    private lateinit var channel: MethodChannel
    private var activity: Activity? = null
    private var gallerySaver: GallerySaver? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, "gallery_saver")
        channel.setMethodCallHandler(this)


    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "saveImage" -> gallerySaver?.checkPermissionAndSaveFile(call, result, MediaType.image)
            "saveVideo" -> gallerySaver?.checkPermissionAndSaveFile(call, result, MediaType.video)
            else -> result.notImplemented()
        }
    }


    override fun onAttachedToActivity(binding: ActivityPluginBinding) {
        this.activity = binding.activity
        gallerySaver = GallerySaver(activity!!)
    }



    override fun onDetachedFromActivityForConfigChanges() {
        print("onDetachedFromActivityForConfigChanges")
    }

    override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
        print("onReattachedToActivityForConfigChanges")
    }

    override fun onDetachedFromActivity() {
        print("onDetachedFromActivity")
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)

    }
}


//constructor(
//        private val gallerySaver: GallerySaver) : MethodCallHandler {
//
//    companion object {
//        @JvmStatic
//        fun registerWith(registrar: Registrar) {
//            val channel = MethodChannel(registrar.messenger(),
//                    "gallery_saver")
//            val gallerySaver = GallerySaver(registrar.activity())
//            registrar.addRequestPermissionsResultListener(gallerySaver)
//            val instance = GallerySaverPlugin(
//                    gallerySaver)
//            channel.setMethodCallHandler(instance)
//        }
//    }
//
//    override fun onMethodCall(call: MethodCall, result: Result) {
//        when (call.method) {
//            "saveImage" -> gallerySaver.checkPermissionAndSaveFile(call, result, MediaType.image)
//            "saveVideo" -> gallerySaver.checkPermissionAndSaveFile(call, result, MediaType.video)
//            else -> result.notImplemented()
//        }
//    }
//}
