package grails.plugin.zipcitystate

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll

@TestFor(ZipCodeController)
@Mock([ZipCode,ZipCodeService])
class ZipCodeControllerSpec extends Specification{

    @Unroll
    void "Lookup for zipcode:#code returns city:#city and state:#state"() {
        given: "a saved ZipCode"
            new ZipCode(code:code, city:city, state:state).save()

        and: "lookup for an existing code"
            controller.lookup(code)

        expect: 'the correct code, city and state'
            response.json.code == expectedCode
            response.json.city == expectedCity
            response.json.state == expectedState

        where:
            code    | city       | state || expectedCode || expectedCity || expectedState
            '02135' | 'Brighton' | 'MA'  || '02135'      || 'Brighton'   || 'MA'
            '02067' | 'Sharon'   | 'MA'  || '02067'      || 'Sharon'     || 'MA'
    }

    void "Lookup for a non-existing zipcode returns no city and state"() {
        when: 'lookup for a non-existing zipcode'
            controller.lookup('00001')

        then: 'response contains no code, city and state'
            !response.json.code
            !response.json.city
            !response.json.state
    }

}