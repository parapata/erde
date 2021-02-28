<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
    xmlns:lxslt="http://xml.apache.org/xslt"
    xmlns:redirect="http://xml.apache.org/xalan/redirect"
<<<<<<< Upstream, based on branch 'develop' of https://github.com/parapata/erde.git
<<<<<<< Upstream, based on branch 'develop' of https://github.com/parapata/erde.git
    xmlns:stringutils="xalan://org.apache.commons.lang3.StringUtils"
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
                <title>ERDE</title>
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
        }
        body {
        }
        ul, li {
            list-style-type: none;
        }
        table {
            width: 100%;
        }
        iframe {
            border: none;
            height: 100%;
            width: 100%;
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
    </xsl:template>

    <xsl:template name="overview-frame.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
            </head>
            <body>
                <ul>
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

    <xsl:template name="overview-summary.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
            </head>
            <body>
                <table>
                    <thead>
                            <th>物理名</th>
                            <th>論理名</th>
                            <th>説明</th>
                    </thead>
                    <tbody>
                        <xsl:for-each select="table">
                            <tr>
                                <td><xsl:value-of select="physicalName" /></td>
                                <td><xsl:value-of select="logicalName" /></td>
                                <td><xsl:value-of select="description" /></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
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
            <xsl:apply-templates select="." mode="table.details" />
        </redirect:write>
    </xsl:template>

    <xsl:template match="table" mode="table.details">
        <html>
            <head>
                <link rel="stylesheet" href="../stylesheet.css" />
                <title><xsl:value-of select="physicalName" /></title>
            </head>
            <body>
                <div>
                    <h2>テーブル <xsl:value-of select="physicalName" /></h2>
                    <dl>
                        <dt>説明</dt>
                        <dd><xsl:value-of select="description" /></dd>
                    </dl>
                    <hr />
                    <!-- 属性の概要 -->
                    <section>
                        <h3>属性の概要</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>一意キー</th>
                                    <th>外部キー</th>
                                    <th>物理名</th>
                                    <th>論理名</th>
                                    <th>型</th>
                                    <th>必須</th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each select="column">
                                    <tr>
                                        <td><xsl:value-of select="position()"/></td>
                                        <td><xsl:value-of select="primaryKey" /></td>
                                        <td></td>
                                        <td><xsl:value-of select="physicalName" /></td>
                                        <td><xsl:value-of select="logicalName" /></td>
                                        <td><xsl:value-of select="type" /></td>
                                        <td><xsl:value-of select="notNull" /></td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </section>

                    <!-- 外部キー -->
                    <section>
                        <h3>外部キー</h3>
                    </section>

                    <!-- 参照キー -->
                    <section>
                        <h3>参照キー</h3>
                    </section>

                    <!-- 複合一意キー -->
                    <section>
                        <h3>複合一意キー</h3>
                    </section>

                    <!-- インデックス の概要 -->
                    <section>
                        <h3>インデックス の概要</h3>
                    </section>

                    <!-- 属性 の詳細 -->
                    <section>
                        <h3>属性の詳細</h3>
                        <xsl:for-each select="column">
                            <h4><xsl:value-of select="physicalName" /></h4>
                            <dl>
                                <dt>説明</dt>
                                <dd><xsl:value-of select="description" /></dd>
                            </dl>
		                    <dl>
                                <dt>型:</dt>
                                <dd><xsl:value-of select="type" /></dd>
                                <dt>一意:</dt>
                                <dd><xsl:value-of select="uniqueKey" /></dd>
                                <dt>必須:</dt>
                                <dd><xsl:value-of select="notNull" /></dd>
                                <dt>初期値:</dt>
                                <dd><xsl:value-of select="defaultValue" /></dd>
                                <dt>自動採番:</dt>
                                <dd><xsl:value-of select="autoIncrement" /></dd>
                                <dt>制約:</dt>
                                <dd><xsl:value-of select="logicalName" /></dd>
		                    </dl>
                            <hr />
                        </xsl:for-each>
                    </section>
=======
=======
<<<<<<< HEAD
>>>>>>> 2dc1dd0 Update: Specification change of html generator
    xmlns:utils="xalan://io.github.erde.generate.html.HtmlGenUtils"
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
                <title>ERDE</title>
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
            vertical-align: top;
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
            width:100%;
            margin:1em auto;
            padding:2em;
            border:#ddd 3px solid;
            border-radius:10px;
            display: block;
        }

        dl.column__details dt {
            float: left;
            clear: both;
            width: 120px;
            height: 24px;
            line-height: 24px;
            text-align: right;
        }

        dl.column__details dd {
            margin-left: 120px;
            width: 400px;
            height: 24px;
            line-height: 24px;
        }

        .site-header{
            background: #afafaf;
            display: flex;
            position: fixed;
            top: 0;
            /*justify-content: space-between;*/
            width: 100%;
            height: 40px;
        }
        .site-logo img{
            height: 20px;
            width: auto;
        }
        .gnav__menu{
            display: flex;
        }
        .gnav__menu__item{
            margin-left: 20px;
        }
        .gnav__menu__item a{
            color: #333;
            text-decoration: none;
        }

    </xsl:template>

    <xsl:template name="overview-frame.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
            </head>
            <body>
                <ul>
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

    <xsl:template name="overview-summary.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
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
                                    <td><xsl:value-of select="description" /></td>
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
            <xsl:apply-templates select="." mode="table.details" />
        </redirect:write>
    </xsl:template>

    <xsl:template match="table" mode="table.details">
        <html>
            <head>
                <link rel="stylesheet" href="../stylesheet.css" />
                <title><xsl:value-of select="physicalName" /></title>
            </head>
            <body>
                <xsl:call-template name="site-header" />
                <div id="content">
                    <h2>テーブル <xsl:value-of select="physicalName" /></h2>
                    <p><xsl:value-of select="description" /></p>
                    <hr />
                    <!-- 属性の概要 -->
                    <div class="box">
                        <h3>属性の概要</h3>
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
                                        <td class="image"><xsl:value-of select="primaryKey" /></td>
                                        <td></td>
                                        <td><a href="#{physicalName}"><xsl:value-of select="physicalName" /></a></td>
                                        <td><xsl:value-of select="logicalName" /></td>
                                        <td><xsl:value-of select="type" /></td>
                                        <td class="image"><xsl:value-of select="notNull" /></td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </div>

                    <!-- 外部キー -->
                    <div class="box">
                        <h3>外部キー</h3>
                        <xsl:for-each select="foreignKey">
                            <dl>
                                <dt><xsl:value-of select="@foreignKeyName" /></dt>
                                <dd>
                                    <ul>
                                        <xsl:for-each select="foreignKeyMapping">
                                            <li><xsl:value-of select="referenceName" />:<xsl:value-of select="targetName" /></li>
                                        </xsl:for-each>
                                    </ul>
                                </dd>
                            </dl>
                        </xsl:for-each>
                    </div>

                    <!-- 複合一意キー -->
                    <div class="box">
                        <h3>複合一意キー</h3>
                        <xsl:for-each select="index">
                            <xsl:if test="@indexType='UNIQUE'">
                                <dl>
                                    <dt><span><xsl:value-of select="@indexName" /></span></dt>
                                    <dd>
                                        <xsl:for-each select="columnName">
                                            <ul>
                                                <li><xsl:value-of select="."/></li>
                                            </ul>
                                        </xsl:for-each>
                                    </dd>
                                </dl>
                            </xsl:if>
                        </xsl:for-each>
                    </div>

                    <!-- インデックスの概要 -->
                    <div class="box">
                        <h3>インデックスの概要</h3>
                        <xsl:for-each select="index">
                            <dl>
                                <dt><span><xsl:value-of select="@indexName" /></span></dt>
                                <dd>
                                    <xsl:for-each select="columnName">
                                        <ul>
                                            <li><xsl:value-of select="."/></li>
                                        </ul>
                                    </xsl:for-each>
                                </dd>
                            </dl>
                        </xsl:for-each>
                    </div>

                    <!-- 属性の詳細 -->
                    <div class="box">
                        <h3>属性の詳細</h3>
                        <xsl:for-each select="column">
                            <div id="{physicalName}" class="details_inner">
                                <h4><xsl:value-of select="physicalName" />:<xsl:value-of select="logicalName" /></h4>
                                <p><xsl:value-of select="description" /></p>
                                <dl class="column__details">
                                    <dt>型:</dt>
                                    <dd><xsl:value-of select="type" /></dd>
                                    <dt>一意:</dt>
                                    <dd><xsl:value-of select="uniqueKey" /></dd>
                                    <dt>必須:</dt>
                                    <dd><xsl:value-of select="notNull" /></dd>
                                    <dt>初期値:</dt>
                                    <dd><xsl:value-of select="defaultValue" /></dd>
                                    <dt>自動採番:</dt>
                                    <dd><xsl:value-of select="autoIncrement" /></dd>
                                    <dt>制約:</dt>
                                    <dd></dd>
                                </dl>
                            </div>
                            <hr />
                        </xsl:for-each>
                    </div>
<<<<<<< Upstream, based on branch 'develop' of https://github.com/parapata/erde.git
>>>>>>> 623f5d8 Work: Update the HTML generator
=======
=======
    xmlns:stringutils="xalan://org.apache.commons.lang3.StringUtils"
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
                <title>ERDE</title>
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
        }
        body {
        }
        ul, li {
            list-style-type: none;
        }
        table {
            width: 100%;
        }
        iframe {
            border: none;
            height: 100%;
            width: 100%;
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
    </xsl:template>

    <xsl:template name="overview-frame.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
            </head>
            <body>
                <ul>
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

    <xsl:template name="overview-summary.html">
        <html>
            <head>
                <link rel="stylesheet" href="stylesheet.css" />
                <title>ERDE</title>
            </head>
            <body>
                <table>
                    <thead>
                            <th>物理名</th>
                            <th>論理名</th>
                            <th>説明</th>
                    </thead>
                    <tbody>
                        <xsl:for-each select="table">
                            <tr>
                                <td><xsl:value-of select="physicalName" /></td>
                                <td><xsl:value-of select="logicalName" /></td>
                                <td><xsl:value-of select="description" /></td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
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
            <xsl:apply-templates select="." mode="table.details" />
        </redirect:write>
    </xsl:template>

    <xsl:template match="table" mode="table.details">
        <html>
            <head>
                <link rel="stylesheet" href="../stylesheet.css" />
                <title><xsl:value-of select="physicalName" /></title>
            </head>
            <body>
                <div>
                    <h2>テーブル <xsl:value-of select="physicalName" /></h2>
                    <dl>
                        <dt>説明</dt>
                        <dd><xsl:value-of select="description" /></dd>
                    </dl>
                    <hr />
                    <!-- 属性の概要 -->
                    <section>
                        <h3>属性の概要</h3>
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>一意キー</th>
                                    <th>外部キー</th>
                                    <th>物理名</th>
                                    <th>論理名</th>
                                    <th>型</th>
                                    <th>必須</th>
                                </tr>
                            </thead>
                            <tbody>
                                <xsl:for-each select="column">
                                    <tr>
                                        <td><xsl:value-of select="position()"/></td>
                                        <td><xsl:value-of select="primaryKey" /></td>
                                        <td></td>
                                        <td><xsl:value-of select="physicalName" /></td>
                                        <td><xsl:value-of select="logicalName" /></td>
                                        <td><xsl:value-of select="type" /></td>
                                        <td><xsl:value-of select="notNull" /></td>
                                    </tr>
                                </xsl:for-each>
                            </tbody>
                        </table>
                    </section>

                    <!-- 外部キー -->
                    <section>
                        <h3>外部キー</h3>
                    </section>

                    <!-- 参照キー -->
                    <section>
                        <h3>参照キー</h3>
                    </section>

                    <!-- 複合一意キー -->
                    <section>
                        <h3>複合一意キー</h3>
                    </section>

                    <!-- インデックス の概要 -->
                    <section>
                        <h3>インデックス の概要</h3>
                    </section>

                    <!-- 属性 の詳細 -->
                    <section>
                        <h3>属性の詳細</h3>
                        <xsl:for-each select="column">
                            <h4><xsl:value-of select="physicalName" /></h4>
                            <dl>
                                <dt>説明</dt>
                                <dd><xsl:value-of select="description" /></dd>
                            </dl>
		                    <dl>
                                <dt>型:</dt>
                                <dd><xsl:value-of select="type" /></dd>
                                <dt>一意:</dt>
                                <dd><xsl:value-of select="uniqueKey" /></dd>
                                <dt>必須:</dt>
                                <dd><xsl:value-of select="notNull" /></dd>
                                <dt>初期値:</dt>
                                <dd><xsl:value-of select="defaultValue" /></dd>
                                <dt>自動採番:</dt>
                                <dd><xsl:value-of select="autoIncrement" /></dd>
                                <dt>制約:</dt>
                                <dd><xsl:value-of select="logicalName" /></dd>
		                    </dl>
                            <hr />
                        </xsl:for-each>
                    </section>
>>>>>>> refs/remotes/origin/develop
>>>>>>> 2dc1dd0 Update: Specification change of html generator
                </div>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
