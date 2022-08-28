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
        builder.was("status", "In Progress")
        assertEquals(mutableListOf("status WAS \"In Progress\""), getClausesValue())
    }

    @Test
    fun wasInZero() {
        assertFailsWith<EmptyListException>(
            message = "A list of one or multiple specified values is expected, but you have given an empty list.",
            block = {
                builder.wasIn("status")
            }
        )
    }

    @Test
    fun wasInOne() {
        builder.wasIn("status", "Resolved")
        assertEquals(mutableListOf("status WAS IN (Resolved)"), getClausesValue())
    }

    @Test
    fun wasInMany() {
        builder.wasIn("status", "Resolved", "Cancelled", "Closed")
        assertEquals(mutableListOf("status WAS IN (Resolved,Cancelled,Closed)"), getClausesValue())
    }

    @Test
    fun wasNotInZero() {
        assertFailsWith<EmptyListException>(
            message = "A list of one or multiple specified values is expected, but you have given an empty list.",
            block = {
                builder.wasNotIn("status")
            }
        )
    }

    @Test
    fun wasNotInOne() {
        builder.wasNotIn("status", "Resolved")
        assertEquals(mutableListOf("status WAS NOT IN (Resolved)"), getClausesValue())
    }

    @Test
    fun wasNotInMany() {
        builder.wasNotIn("status", "Resolved", "Cancelled", "Closed")
        assertEquals(mutableListOf("status WAS NOT IN (Resolved,Cancelled,Closed)"), getClausesValue())
    }

    @Test
    fun wasNot() {
        builder.wasNot("status", "In Progress")
        assertEquals(mutableListOf("status WAS NOT \"In Progress\""), getClausesValue())
    }

    @Test
    fun changed() {
        builder.changed("assignee")
        assertEquals(mutableListOf("assignee CHANGED"), getClausesValue())
    }

    @Test
    fun andWithEmptyClauses() {
        builder.and()
        assertTrue(getClausesValue().isEmpty())
    }

    @Test
    fun andWithAnotherKeywordNextToIt() {
        builder.equal("project", "Software").and().and()
        assertEquals(mutableListOf("project = Software", "AND"), getClausesValue())
    }

    @Test
    fun and() {
        builder.equal("project", "Software").and()
        assertEquals(mutableListOf("project = Software", "AND"), getClausesValue())
    }

    @Test
    fun orWithEmptyClauses() {
        builder.or()
        assertTrue(getClausesValue().isEmpty())
    }

    @Test
    fun orWithAnotherKeywordNextToIt() {
        builder.equal("project", "Hardware").and().or()
        assertEquals(mutableListOf("project = Hardware", "AND"), getClausesValue())
    }

    @Test
    fun or() {
        builder.equal("project", "Hardware").or()
        assertEquals(mutableListOf("project = Hardware", "OR"), getClausesValue())
    }

    @Test
    fun inProjectWithEmptyClauses() {
        builder.inProject("Unicorn")
        assertEquals(mutableListOf("project = Unicorn"), getClausesValue())
    }

    @Test
    fun inProjectWithExistingClauses() {
        builder.equal("assignee", "johndoe").inProject("Unicorn")
        assertEquals(mutableListOf("assignee = johndoe", "AND", "project = Unicorn"), getClausesValue())
    }

    @Test
    fun withIssueTypeWithEmptyClauses() {
        builder.withIssueType("Task")
        assertEquals(mutableListOf("type = Task"), getClausesValue())
    }

    @Test
    fun withIssueTypeWithExistingClauses() {
        builder.equal("assignee", "johndoe").withIssueType("Task")
        assertEquals(mutableListOf("assignee = johndoe", "AND", "type = Task"), getClausesValue())
    }

    @Test
    fun reportByWithEmptyClauses() {
        builder.reportBy("lee")
        assertEquals(mutableListOf("reporter = lee"), getClausesValue())
    }

    @Test
    fun reportByWithExistingClauses() {
        builder.equal("project", "Unicorn").reportBy("lee")
        assertEquals(mutableListOf("project = Unicorn", "AND", "reporter = lee"), getClausesValue())
    }

    @Test
    fun assignToWithEmptyClauses() {
        builder.assignTo("jenkins")
        assertEquals(mutableListOf("assignee = jenkins"), getClausesValue())
    }

    @Test
    fun assignToWithExistingClauses() {
        builder.equal("project", "Unicorn").assignTo("jenkins")
        assertEquals(mutableListOf("project = Unicorn", "AND", "assignee = jenkins"), getClausesValue())
    }

    @Test
    fun inSprintWithEmptyClauses() {
        builder.inSprint("2022CW01")
        assertEquals(mutableListOf("sprint = 2022CW01"), getClausesValue())
    }

    @Test
    fun inSprintWithExistingClauses() {
        builder.equal("project", "Unicorn").inSprint("2022CW01")
        assertEquals(mutableListOf("project = Unicorn", "AND", "sprint = 2022CW01"), getClausesValue())
    }

    @Test
    fun quoteStringWithSpace() {
        builder.inEitherOf("status", "Resolved", "In Progress", "Closed")
        assertEquals(mutableListOf("status in (Resolved,\"In Progress\",Closed)"), getClausesValue())
    }

    @Test
    fun build() {
        val jql = builder
            .equal("assignee", "lee")
            .and()
            .lessThan("created", "startOfDay(-0d)")
            .or()
            .inEitherOf("project", "Software", "Hardware", "Business")
            .assignTo("jenkins")
            .build()
        assertEquals("assignee = lee AND created < startOfDay(-0d) OR project in (Software,Hardware,Business) AND assignee = jenkins", jql.jql)
    }
}