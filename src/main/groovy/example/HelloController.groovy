package example

import com.fasterxml.jackson.core.JsonParseException
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Error
import io.micronaut.http.hateos.JsonError
import io.micronaut.http.hateos.Link

@Controller("/hello")
class HelloController {

    // https://docs.micronaut.io/latest/guide/index.html#_local_error_handling
    @Error
    HttpResponse<JsonError> jsonError(HttpRequest request, JsonParseException jsonParseException) {

        def error = new JsonError("Invalid JSON: " + jsonParseException.getMessage())
                .link(Link.SELF, Link.of(request.getUri()));

        return HttpResponse.<JsonError> status(HttpStatus.BAD_REQUEST, "Fix Your JSON")
                .body(error) as HttpResponse<JsonError>
    }
}
