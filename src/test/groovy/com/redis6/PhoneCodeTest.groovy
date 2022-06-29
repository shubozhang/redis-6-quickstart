package com.redis6

import spock.lang.Specification
import static org.junit.Assert.assertTrue

class PhoneCodeTest extends Specification {

    def "test phone code verification"(){
        given:
        String phone = "123456"

        when:
        String vcode = PhoneCode.getCode(phone)

        then:
        assertTrue(PhoneCode.verifyCode(phone, vcode))

    }
}
