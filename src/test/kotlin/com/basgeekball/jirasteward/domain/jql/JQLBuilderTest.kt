package com.basgeekball.jirasteward.domain.jql

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.reflect.Field

internal class JQLBuilderTest {
    private lateinit var builder: JQLBuilder
    private lateinit var clausesField: Field

    @BeforeEach
    fun setUp() {
        builder = JQLBuilder()
        clausesField = JQLBuilder::class.java.getDeclaredField("clauses")
        clausesField.isAccessible = true
    }

    @Suppress("UNCHECKED_CAST")
    private fun getClausesValue(): MutableList<String> {
        return clausesField.get(builder) as MutableList<String>
    }

    @Test
    fun equal() {
        builder.equal("assignee", "johndoe")
        assertEquals(mutableListOf("assignee = johndoe"), getClausesValue())
    }

    @Test
    fun notEqual() {
    }

    @Test
    fun greaterThan() {
    }

    @Test
    fun greaterThanOrEqualTo() {
    }

    @Test
    fun lessThan() {
    }

    @Test
    fun lessThanOrEqualTo() {
    }

    @Test
    fun inEitherOf() {
    }

    @Test
    fun notInAnyOf() {
    }

    @Test
    fun contain() {
    }

    @Test
    fun containWildcardMatch() {
    }

    @Test
    fun containMultipleWords() {
    }

    @Test
    fun containExactPhrase() {
    }

    @Test
    fun notContain() {
    }

    @Test
    fun haveNoValue() {
    }

    @Test
    fun haveAValue() {
    }

    @Test
    fun was() {
    }

    @Test
    fun wasIn() {
    }

    @Test
    fun wasNotIn() {
    }

    @Test
    fun wasNot() {
    }

    @Test
    fun changed() {
    }

    @Test
    fun and() {
    }

    @Test
    fun or() {
    }

    @Test
    fun inProject() {
    }

    @Test
    fun withIssueType() {
    }

    @Test
    fun reportBy() {
    }

    @Test
    fun assignTo() {
    }

    @Test
    fun build() {
    }
}