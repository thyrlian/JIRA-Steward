package com.basgeekball.jirasteward.domain.jql

import com.basgeekball.jirasteward.domain.jql.exception.EmptyListException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.lang.reflect.Field
import kotlin.test.assertFailsWith

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
        builder.notEqual("reporter", "mmustermann")
        assertEquals(mutableListOf("reporter != mmustermann"), getClausesValue())
    }

    @Test
    fun greaterThan() {
        builder.greaterThan("created", "startOfDay(-0d)")
        assertEquals(mutableListOf("created > startOfDay(-0d)"), getClausesValue())
    }

    @Test
    fun greaterThanOrEqualTo() {
        builder.greaterThanOrEqualTo("created", "startOfDay(-0d)")
        assertEquals(mutableListOf("created >= startOfDay(-0d)"), getClausesValue())
    }

    @Test
    fun lessThan() {
        builder.lessThan("created", "startOfDay(-0d)")
        assertEquals(mutableListOf("created < startOfDay(-0d)"), getClausesValue())
    }

    @Test
    fun lessThanOrEqualTo() {
        builder.lessThanOrEqualTo("created", "startOfDay(-0d)")
        assertEquals(mutableListOf("created <= startOfDay(-0d)"), getClausesValue())
    }

    @Test
    fun inEitherOfZero() {
        assertFailsWith<EmptyListException>(
            message = "A list of one or multiple specified values is expected, but you have given an empty list.",
            block = {
                builder.inEitherOf("project")
            }
        )
    }

    @Test
    fun inEitherOfOne() {
        builder.inEitherOf("project", "Software")
        assertEquals(mutableListOf("project in (Software)"), getClausesValue())
    }

    @Test
    fun inEitherOfMany() {
        builder.inEitherOf("project", "Software", "Hardware", "Business")
        assertEquals(mutableListOf("project in (Software,Hardware,Business)"), getClausesValue())
    }

    @Test
    fun notInAnyOfZero() {
        assertFailsWith<EmptyListException>(
            message = "A list of one or multiple specified values is expected, but you have given an empty list.",
            block = {
                builder.notInAnyOf("project")
            }
        )
    }

    @Test
    fun notInAnyOfOne() {
        builder.notInAnyOf("project", "Software")
        assertEquals(mutableListOf("project not in (Software)"), getClausesValue())
    }

    @Test
    fun notInAnyOfMany() {
        builder.notInAnyOf("project", "Software", "Hardware", "Business")
        assertEquals(mutableListOf("project not in (Software,Hardware,Business)"), getClausesValue())
    }

    @Test
    fun contain() {
        builder.contain("summary", "foobar")
        assertEquals(mutableListOf("summary ~ foobar"), getClausesValue())
    }

    @Test
    fun containWildcardMatch() {
        builder.containWildcardMatch("summary", "foobar")
        assertEquals(mutableListOf("summary ~ \"foobar*\""), getClausesValue())
    }

    @Test
    fun containMultipleWordsZero() {
        assertFailsWith<EmptyListException>(
            message = "A list of one or multiple specified values is expected, but you have given an empty list.",
            block = {
                builder.containMultipleWords("summary")
            }
        )
    }

    @Test
    fun containMultipleWordsOne() {
        builder.containMultipleWords("summary", "foobar")
        assertEquals(mutableListOf("summary ~ \"foobar\""), getClausesValue())
    }

    @Test
    fun containMultipleWordsOneWithMany() {
        builder.containMultipleWords("summary", "foo bar")
        assertEquals(mutableListOf("summary ~ \"foo bar\""), getClausesValue())
    }

    @Test
    fun containMultipleWordsMany() {
        builder.containMultipleWords("summary", "foo", "bar", "baz")
        assertEquals(mutableListOf("summary ~ \"foo bar baz\""), getClausesValue())
    }

    @Test
    fun containExactPhrase() {
        builder.containExactPhrase("summary", "foo bar")
        assertEquals(mutableListOf("summary ~ \"\\\"foo bar\\\"\""), getClausesValue())
    }

    @Test
    fun notContain() {
        builder.notContain("summary", "foobar")
        assertEquals(mutableListOf("summary !~ foobar"), getClausesValue())
    }

    @Test
    fun haveNoValue() {
        builder.haveNoValue("fixVersion")
        assertEquals(mutableListOf("fixVersion is empty"), getClausesValue())
    }

    @Test
    fun haveAValue() {
        builder.haveAValue("fixVersion")
        assertEquals(mutableListOf("fixVersion is not empty"), getClausesValue())
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