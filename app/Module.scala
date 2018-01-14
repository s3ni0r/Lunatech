import api.{Api, ApiImpl}
import com.google.inject.AbstractModule

class Module extends AbstractModule {
  def configure = {
    bind(classOf[Api]).to(classOf[ApiImpl]).asEagerSingleton()
  }
}
