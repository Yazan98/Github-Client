package com.yazan98.autohub

import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.core.ImageTranscoderType
import com.facebook.imagepipeline.core.MemoryChunkType
import com.google.firebase.FirebaseApp
import com.yazan98.autohub.starter.GithubStarter
import com.yazan98.autohub.utils.LeakUploader
import com.yazan98.data.ApplicationPrefs
import com.yazan98.domain.models.*
import io.realm.Realm
import io.realm.RealmConfiguration
import io.vortex.android.keys.LoggerType
import io.vortex.android.logger.config.VortexLoggerConfiguration
import io.vortex.android.logger.config.VortexLoggerOptions
import io.vortex.android.logger.keys.VortexLoggerLevel
import io.vortex.android.logger.keys.VortexLoggerMode
import io.vortex.android.logger.keys.VortexLoggingStatus
import io.vortex.android.models.ui.VortexNotificationDetails
import io.vortex.android.prefs.VortexPrefsConfig
import io.vortex.android.ui.VortexMessageDelegation
import io.vortex.android.utils.VortexApplication
import io.vortex.android.utils.VortexConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import leakcanary.AppWatcher
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class AutohubApplication : VortexApplication(), Thread.UncaughtExceptionHandler {

    companion object {
        const val SHARED_PREFS_NAME = "AutoHubPrefs"
        const val SHARED_PREFS_MODE = Context.MODE_PRIVATE
        const val DATABASE_NAME = "autohub.realm"
        const val AUTOHUB_LOGGING_TAG = "AutoHub"
        const val DATABASE_VERSION = 1L
    }

    private lateinit var githubStarter: GithubStarter
    private val messageController: VortexMessageDelegation by lazy {
        VortexMessageDelegation()
    }

    override fun onCreate() {
        super.onCreate()

        AutohubScope.launch {
            configNotifications()
            VortexConfiguration
                .registerLeakCanaryConfiguration()
                .registerApplicationClass(this@AutohubApplication)
                .registerApplicationState(true)
                .registerApplicationLogger(LoggerType.TIMBER)
                .registerCompatVector()
                .registerStrictMode()
                .registerExceptionHandler(this@AutohubApplication)
                .registerVortexPermissionsSettings()

            applyVortexLoggerConfiguration()
            AppWatcher.config = AppWatcher.config.copy(watchFragmentViews = true)
            LeakCanary.config = LeakCanary.config.copy(onHeapAnalyzedListener = LeakUploader())

            try {
                applicationContext?.let {
                    Realm.init(it)
                    Realm.getInstance(setupRealmConfiguration())
                }
            } catch (ex: Exception) {
                Timber.e("Realm Error : ${ex.message}")
                handleDatabaseError(ex.message)
            }

        }

        FirebaseApp.initializeApp(this)
        VortexPrefsConfig.prefs = getSharedPreferences(SHARED_PREFS_NAME, SHARED_PREFS_MODE)

        Fresco.initialize(
            applicationContext,
            ImagePipelineConfig.newBuilder(applicationContext)
                .setMemoryChunkType(MemoryChunkType.BUFFER_MEMORY)
                .setImageTranscoderType(ImageTranscoderType.JAVA_TRANSCODER)
                .experiment().setNativeCodeDisabled(true)
                .build()
        )

        startKoin {
            androidLogger(Level.DEBUG)
            modules(appModules)
        }
    }

    private suspend fun applyVortexLoggerConfiguration() {
        withContext(Dispatchers.IO) {
            VortexLoggerConfiguration.init(
                VortexLoggerOptions(
                    globalTag = AUTOHUB_LOGGING_TAG,
                    postMode = VortexLoggerMode.EQUAL,
                    loggingStatus = VortexLoggingStatus.ENABLED,
                    defaultLevel = VortexLoggerLevel.DEBUG,
                    preMode = VortexLoggerMode.EQUAL
                )
            )
        }
    }

    suspend fun startGithubActions() {
        withContext(Dispatchers.IO) {
            if (ApplicationPrefs.getToken().isNotEmpty()) {
                githubStarter = GithubStarter(ApplicationPrefs.getUsername()).apply {
                    this.startActions()
                }
            }
        }
    }

    private val appModules: Module = module {
        viewModel { NotificationsViewModel() }
        viewModel { StarsViewModel() }
        viewModel { ProfileViewModel() }
        viewModel { FollowingViewModel() }
        viewModel { RepositoryViewModel() }
        viewModel { FeedsViewModel() }
        viewModel { TrendingRepositoryViewModel() }
        viewModel { CommitsViewModel() }
        viewModel { ContrbutersViewModel() }
    }

    private suspend fun configNotifications() {
        withContext(Dispatchers.IO) {
            applicationContext?.let {
                notificationsController.createMultiNotificationChannels(
                    arrayListOf(
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1"),
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1"),
                        VortexNotificationDetails("Offers", "Offers Channel", "fdsgd15d3fg1")
                    ),
                    it
                )
            }
        }
    }

    private suspend fun setupRealmConfiguration() = suspendCoroutine<RealmConfiguration> {
        try {
            val config = RealmConfiguration.Builder()
                .name(DATABASE_NAME)
                .schemaVersion(DATABASE_VERSION)
                .inMemory()
                .build()
            it.resume(config)
        } catch (ex: Exception) {
            it.resumeWithException(ex)
        }
    }

    private suspend fun handleDatabaseError(message: String?) {
        withContext(Dispatchers.Main) {
            message?.let { result ->
                applicationContext?.let {
                    messageController.showLongMessage(result, it)
                }
            }
        }
    }

    override fun uncaughtException(t: Thread, e: Throwable) {

    }

}

