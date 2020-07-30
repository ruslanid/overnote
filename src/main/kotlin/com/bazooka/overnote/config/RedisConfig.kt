package com.bazooka.overnote.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate

@Configuration
class RedisConfig(
        @Value("\${redis.host}") private val host: String,
        @Value("\${redis.port}") private val port: String,
        @Value("\${redis.password}") private val password: String
) {
    @Bean
    fun lettuceConnectionFactory(): LettuceConnectionFactory? {
        val config = RedisStandaloneConfiguration(host, port.toInt())
        config.setPassword(password)

        return LettuceConnectionFactory(config)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *>? {
        val template = RedisTemplate<ByteArray, ByteArray>()
        template.setConnectionFactory(lettuceConnectionFactory()!!)

        return template
    }
}