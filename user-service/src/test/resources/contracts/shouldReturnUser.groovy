package contacts

org.springframework.cloud.contract.spec.Contract.make {
    request {
        method 'GET'
        url '/uaa/v1/me'
        headers {
            header('Content-Type': consumer(regex('application/json.*')))
        }
    }
    response {
        status 200
        body([
            username : value(producer(regex('[A-Za-z0-9]+'))),
            firstName : value(producer(regex('[A-Za-z]+'))),
            lastName : value(producer(regex('[A-Za-z]+'))),
            id : value(producer(regex('[0-9]+')))
        ])
        headers {
            header('Content-Type': value(
                    producer('application/json;charset=UTF-8'),
                    consumer('application/json;charset=UTF-8'))
            )
        }
    }
}