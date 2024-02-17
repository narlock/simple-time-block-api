import spock.lang.Specification

class HelloSpec extends Specification {
    def 'hello world'() {
        expect:
        'hello' != 'world'
    }
}
