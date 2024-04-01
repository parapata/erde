<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:lxslt="http://xml.apache.org/xslt"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
    xmlns:utils="xalan://io.github.parapata.erde.generate.html.HtmlGenUtils"
    extension-element-prefixes="redirect">

    <xsl:output method="html" indent="yes" encoding="UTF-8" />
    <xsl:decimal-format decimal-separator="." grouping-separator="," />
    <xsl:param name="output.dir" />

    <xsl:template match="erde">

        <!-- create the index.html -->
        <xsl:call-template name="index.html" />

        <!-- create the stylesheet.css -->
        <redirect:write file="{$output.dir}/stylesheet.css">
            <xsl:call-template name="stylesheet.css" />
        </redirect:write>

        <xsl:apply-templates select="diagram" />

    </xsl:template>

    <xsl:template name="index.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>Database Definition</title>
            </head>
            <body>
                <div id="left">
                    <iframe name="left" src="overview-frame.html"></iframe>
                </div>
                <div id="right">
                    <iframe name="right" src="overview-summary.html"></iframe>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="stylesheet.css">
        html {
            font-family: "Hiragino Kaku Gothic Pro", "ヒラギノ角ゴ Pro", "Yu Gothic Medium", "游ゴシック Medium", YuGothic, "游ゴシック体", "メイリオ", sans-serif;
            font-size: 62.5%;
        }

        body {
            font-size: 1.4em;
            margin: 0;
            padding: 0;
        }

        iframe {
            border: none;
            height: 100%;
            width: 100%;
        }

        img {
            max-width: 100%;
            height: auto;
        }

        a {
            text-decoration:none;
        }

        ul, ol {
            list-style-type: none;
        }

        table, th, td {
            width: 100%;
            border-collapse: collapse;
            border: 1px solid #ccc;
            line-height: 1.5;
        }

        table.table {
            table-layout: fixed;
        }

        table.table th {
            width: 150px;
            font-weight: bold;
            vertical-align: top;
            background: #3f3f3f;
            color: #ffffff;
        }

        .table tr:hover {
            background-color: #d9efff;
        }

        table.table td a {
            display:block;
            width:100%;
            height:100%;
        }

        table.table td {
            width:100%;
            padding: 0 10px;
        }

        tr:nth-child(even) {
            background: #d9d9d9;
        }

        .col1 {
            width: 20px;
        }

        .col2 {
            width: 30px;
        }

        .col3 {
            width: 30px;
        }

        .col7 {
            width: 30px;
        }

        td.number {
            text-align: right;
        }

        td.image {
            text-align: center;
        }

        #left {
            position: absolute;
            top: 0;
            left: 0px;
            width: 300px;
            height: 100%;
            text-align: center;
        }

        #right {
            position: absolute;
            top: 0;
            right: 0;
            left: 300px;
            height: 100%;
            border-left: 2px solid #ccc;
        }

        #content {
            margin: 2em;
        }

        .box {
            box-sizing: border-box;
            width: 100%;
            margin: 1em auto;
            padding: 0 1.5em 1em 1.5em;
            border: #ddd 3px solid;
            border-radius: 10px;
            display: block;
        }

        dl.column__definition dt {
            float: left;
            clear: both;
            width: 120px;
            height: 24px;
            line-height: 24px;
            text-align: right;
            padding: 0 10 0 0;
        }

        dl.column__definition dd {
            margin-left: 120px;
            width: 400px;
            height: 24px;
            line-height: 24px;
        }

        span.separator {
            padding: 0 5px;
        }

        .navi__menu {
            padding: 20px 30px;
        }
    </xsl:template>

    <!-- ********************************************************** -->
    <!-- サイドメニュー・ページ                                     -->
    <!-- ********************************************************** -->
    <xsl:template name="overview-frame.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
            </head>
            <body>
                <ul class="navi__menu">
                    <li>
                        <a href="./overview-summary.html" target="right">
                            HOME
                        </a>
                    </li>
                    <xsl:for-each select="table">
                    <li>
                        <a href="./table/{physicalName}.html" target="right">
                            <xsl:value-of select="physicalName" />
                        </a>
                    </li>
                    </xsl:for-each>
                </ul>
            </body>
        </html>
    </xsl:template>

    <!-- ********************************************************** -->
    <!-- サマリー・ページ                                           -->
    <!-- ********************************************************** -->
    <xsl:template name="overview-summary.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>Summary</title>
            </head>
            <body>
                <xsl:call-template name="site-header" />
                <div id="content">
                    <table class="table">
                        <thead>
                            <th><xsl:value-of select="utils:getResource('html.table.physicalName')" /></th>
                            <th><xsl:value-of select="utils:getResource('html.table.logicalName')" /></th>
                            <th><xsl:value-of select="utils:getResource('html.table.description')" /></th>
                        </thead>
                        <tbody>
                            <xsl:for-each select="table">
                            <tr>
                                <td><a href="./table/{physicalName}.html" target="right"><xsl:value-of select="physicalName" /></a></td>
                                <td><xsl:value-of select="logicalName" /></td>
                                <td><xsl:value-of select="utils:escapeHTML(description)" disable-output-escaping="yes" /></td>
                            </tr>
                            </xsl:for-each>
                        </tbody>
                    </table>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template name="site-header">
    </xsl:template>

    <xsl:template match="diagram">

        <redirect:write file="{$output.dir}/overview-frame.html">
            <xsl:call-template name="overview-frame.html" />
        </redirect:write>

        <redirect:write file="{$output.dir}/overview-summary.html">
            <xsl:call-template name="overview-summary.html" />
        </redirect:write>

        <xsl:apply-templates select="table" />

    </xsl:template>

    <xsl:template match="table">
        <redirect:write file="{$output.dir}/table/{physicalName}.html">
            <xsl:apply-templates select="." mode="table.definition" />
        </redirect:write>
    </xsl:template>

    <!-- ********************************************************** -->
    <!-- テーブル定義・ページ                                       -->
    <!-- ********************************************************** -->
    <xsl:template match="table" mode="table.definition">
        <xsl:variable name="table" select="."/>
        <html>
            <head>
                <link rel="stylesheet" href="../stylesheet.css" />
                <title><xsl:value-of select="physicalName" /></title>
            </head>
            <body>
                <xsl:call-template name="site-header" />
                <div id="content">
                    <h2><xsl:value-of select="utils:getResource('html.page.title.table')" /><span class="separator">:</span><xsl:value-of select="physicalName" /></h2>
                    <p><xsl:value-of select="utils:escapeHTML(description)" disable-output-escaping="yes" /></p>
                    <hr />
                    <!-- 属性の概要 -->
                    <div class="box">
                        <h3><xsl:value-of select="utils:getResource('html.page.section.title.attribute')" /></h3>
                        <table class="table">
                            <colgroup>
                                <col class="col1" />
                                <col class="col2" />
                                <col class="col3" />
                                <col class="col4" />
                                <col class="col5" />
                                <col class="col6" />
                                <col class="col7" />
                            </colgroup>

                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th><xsl:value-of select="utils:getResource('html.column.primaryKey')" /></th>
                                    <th><xsl:value-of select="utils:getResource('html.foreignKeys')" /></th>
                                    <th><xsl:value-of select="utils:getResource('html.column.physicalName')" /></th>
                                    <th><xsl:value-of select="utils:getResource('html.column.logicalName')" /></th>
                                    <th><xsl:value-of select="utils:getResource('html.column.type')" /></th>
                                    <th><xsl:value-of select="utils:getResource('html.column.notNull')" /></th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each select="column">
                                <tr>
                                    <td class="number"><xsl:value-of select="position()"/></td>
                                    <td class="image"><xsl:if test="primaryKey"><img src="../check.svg" /></xsl:if></td>
                                    <td class="image"><xsl:if test="utils:isForeignKey($table, physicalName)"><img src="../check.svg" /></xsl:if></td>
                                    <td><a href="#{physicalName}"><xsl:value-of select="physicalName" /></a></td>
                                    <td><xsl:value-of select="logicalName" /></td>
                                    <td><xsl:value-of select="utils:getType(type, columnSize, decimal, unsigned)" /></td>
                                    <td class="image"><xsl:if test="notNull"><img src="../check.svg" /></xsl:if></td>
                                </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                    <!-- 外部キー -->
                    <xsl:if test="foreignKey">
                    <div class="box">
                        <h3><xsl:value-of select="utils:getResource('html.page.section.title.foreignKeys')" /></h3>
                        <xsl:for-each select="foreignKey">
                            <xsl:variable name="sourceId" select="@sourceId"/>
                            <dl>
                                <dt><xsl:value-of select="@foreignKeyName" /></dt>
                                <dd>
                                    <ul>
                                        <xsl:for-each select="foreignKeyMapping">
                                        <li>
                                            <xsl:value-of select="utils:toSourceColumeName($table, $sourceId, referenceName)" />
                                            <span class="separator">:</span>
                                            <xsl:value-of select="targetName" />
                                        </li>
                                        </xsl:for-each>
                                    </ul>
                                </dd>
                            </dl>
                        </xsl:for-each>
                    </div>
                    </xsl:if>

                    <!-- 複合一意キーの概要 -->
                    <xsl:if test="utils:isUniqeIndexEmpty(index)">
                    <div class="box">
                        <h3><xsl:value-of select="utils:getResource('html.page.section.title.compositeUniqueKeys')" /></h3>
                        <xsl:for-each select="index">
                            <xsl:if test="@indexType='UNIQUE'">
                                <dl>
                                    <dt><span><xsl:value-of select="@indexName" /></span></dt>
                                    <dd>
                                        <xsl:for-each select="columnName">
                                        <ul><li><xsl:value-of select="."/></li></ul>
                                        </xsl:for-each>
                                    </dd>
                                </dl>
                            </xsl:if>
                        </xsl:for-each>
                    </div>
                    </xsl:if>

                    <!-- インデックスの概要 -->
                    <xsl:if test="utils:isIndexEmpty(index)">
                    <div class="box">
                        <h3><xsl:value-of select="utils:getResource('html.page.section.title.index')" /></h3>
                        <xsl:for-each select="index">
                            <dl>
                                <dt><span><xsl:value-of select="@indexName" /></span></dt>
                                <dd>
                                    <xsl:for-each select="columnName">
                                    <ul><li><xsl:value-of select="."/></li></ul>
                                    </xsl:for-each>
                                </dd>
                            </dl>
                        </xsl:for-each>
                    </div>
                    </xsl:if>

                    <!-- 属性の詳細 -->
                    <div class="box">
                        <h3><xsl:value-of select="utils:getResource('html.page.section.title.attribute')" /></h3>
                        <xsl:for-each select="column">
                        <div id="{physicalName}" class="details_inner">
                            <h4><xsl:value-of select="physicalName" /><xsl:if test="primaryKey">(PK)</xsl:if><span class="separator">:</span><xsl:value-of select="logicalName" /></h4>
                            <p><xsl:value-of select="utils:escapeHTML(description)" disable-output-escaping="yes" /></p>
                            <dl class="column__definition">
                                <dt><xsl:value-of select="utils:getResource('html.column.type')" />:</dt>
                                <dd><xsl:value-of select="utils:getType(type, columnSize, decimal, unsigned)" /></dd>
                                <dt><xsl:value-of select="utils:getResource('html.column.unique')" />:</dt>
                                <dd><xsl:value-of select="utils:toBoolean(uniqueKey)" /></dd>
                                <dt><xsl:value-of select="utils:getResource('html.column.notNull')" />:</dt>
                                <dd><xsl:value-of select="utils:toBoolean(notNull)" /></dd>
                                <dt><xsl:value-of select="utils:getResource('html.column.defaultValue')" />:</dt>
                                <dd><xsl:value-of select="defaultValue" /></dd>
                                <dt><xsl:value-of select="utils:getResource('html.column.autoIncrement')" />:</dt>
                                <dd><xsl:value-of select="utils:toBoolean(autoIncrement)" /></dd>
                            </dl>
                        </div>
                        <hr />
                        </xsl:for-each>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
