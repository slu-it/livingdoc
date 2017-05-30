package utils

import org.junit.platform.engine.*
import org.junit.platform.engine.discovery.DiscoverySelectors
import org.junit.platform.engine.discovery.PackageSelector
import org.junit.platform.engine.reporting.ReportEntry
import org.livingdoc.junit.engine.LivingDocTestEngine
import java.util.*

class PackageExecutor {

    fun execute(packageName: String) {

        val engine = LivingDocTestEngine()
        val discoveryRequest = PackageDiscoveryRequest(packageName)

        val uniqueId = UniqueId.forEngine(engine.id)
        val testDescriptor = engine.discover(discoveryRequest, uniqueId)

        val executionListener = NoOpEngineExecutionListener()
        val configurationParameters = NoConfigurationParameters()
        val executionRequest = ExecutionRequest(testDescriptor, executionListener, configurationParameters)
        engine.execute(executionRequest)

    }

    class PackageDiscoveryRequest(
            private val packageName: String
    ) : EngineDiscoveryRequest {
        override fun <T : DiscoverySelector> getSelectorsByType(selectorType: Class<T>): MutableList<T> {
            if (selectorType == PackageSelector::class.java) {
                val list = mutableListOf<T>()
                list.add(DiscoverySelectors.selectPackage(packageName) as T)
                return list
            }
            return mutableListOf()
        }

        override fun getConfigurationParameters(): ConfigurationParameters = NoConfigurationParameters()
        override fun <T : DiscoveryFilter<*>?> getDiscoveryFiltersByType(filterType: Class<T>): MutableList<T> = mutableListOf()
    }

    class NoConfigurationParameters : ConfigurationParameters {
        override fun size(): Int = 0
        override fun get(key: String): Optional<String> = Optional.empty()
        override fun getBoolean(key: String): Optional<Boolean> = Optional.empty()
    }

    class NoOpEngineExecutionListener : EngineExecutionListener {

        override fun executionFinished(testDescriptor: TestDescriptor?, testExecutionResult: TestExecutionResult?) {
            println("executionFinished: testDescriptor=[$testDescriptor], testExecutionResult=[$testExecutionResult]")
        }

        override fun reportingEntryPublished(testDescriptor: TestDescriptor?, entry: ReportEntry?) {
            println("reportingEntryPublished: testDescriptor=[$testDescriptor], entry=[$entry]")
        }

        override fun executionSkipped(testDescriptor: TestDescriptor?, reason: String?) {
            println("executionSkipped: testDescriptor=[$testDescriptor], reason=[$reason]")
        }

        override fun executionStarted(testDescriptor: TestDescriptor?) {
            println("executionStarted: testDescriptor=[$testDescriptor]")
        }

        override fun dynamicTestRegistered(testDescriptor: TestDescriptor?) {
            println("dynamicTestRegistered: testDescriptor=[$testDescriptor]")
        }

    }

}
