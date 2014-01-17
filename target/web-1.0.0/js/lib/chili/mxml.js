/*
===============================================================================
Chili is the jQuery code highlighter plugin
...............................................................................
LICENSE: http://www.opensource.org/licenses/mit-license.php
WEBSITE: http://noteslog.com/chili/

                                               Copyright 2008 / Andrea Ercolino
===============================================================================

This file is based on html.js provided by Andrea Ercolino.  It has been renamed 
and modified by James Bancroft (jim dot bancroft at gmail dot com) to better 
support MXML.

For more information, see http://jamesbancroft.homeip.net/
*/

{
	  _name: 'mxml'
	, _case: false
	, _main: {
		  doctype: { 
			  _match: /<!DOCTYPE\b[\w\W]*?>/ 
			, _style: "color: #CC6600;"
		}
		, ie_style: {
			  _match: /(<!--\[[^\]]*\]>)([\w\W]*?)(<!\[[^\]]*\]-->)/
			, _replace: function( all, open, content, close ) {
				return "<span class='ie_style'>" + this.x( open ) + "</span>" 
					  + this.x( content, '//style' ) 
					  + "<span class='ie_style'>" + this.x( close ) + "</span>";
			}
			, _style: "color: DarkSlateGray; font-weight: bold;"
		}
		, comment: { 
			  _match: /<!--[\w\W]*?-->/ 
			, _style: "color: #cc0000;"
		}
		, script: { 
			  _match: /(<script\s+[^>]*>)([\w\W]*?)(<\/script\s*>)/
			, _replace: function( all, open, content, close ) { 
				  return this.x( open, '//tag_start' ) 
					  + this.x( content, 'js' ) 
					  + this.x( close, '//tag_end' );
			} 
		}
		, style: { 
			  _match: /(<style\s+[^>]*>)([\w\W]*?)(<\/style\s*>)/
			, _replace: function( all, open, content, close ) { 
				  return this.x( open, '//tag_start' ) 
					  + this.x( content, 'css' ) 
					  + this.x( close, '//tag_end' );
			} 
		}
		// matches a starting tag of an element (with attrs)
		// like "<div ... >" or "<img ... />"
		, tag_start: { 
			  _match: /(<\w+\S+)((?:[?%]>|[\w\W])*?)(\/>|>)/ 
			, _replace: function( all, open, content, close ) { 
				  return "<span class='tag_start'>" + this.x( open ) + "</span>" 
					  + this.x( content, '/tag_attrs' ) 
					  + "<span class='tag_start'>" + this.x( close ) + "</span>";
			}
			, _style: "color: #729fcf; font-weight: bold;"
		} 
		// matches an ending tag
		// like "</div>"
		, tag_end: { 
			  _match: /<\/\w+\S*\s*>|\/>/ 
			, _style: "color: #729fcf; font-weight: bold;"
		}
		, entity: { 
			  _match: /&\w+?;/ 
			, _style: "color: #067a3e;"
		}
	}
	, tag_attrs: {
		// matches a name/value pair
		attr: {
			// before in $1, name in $2, between in $3, value in $4
			  _match: /(\W*?)([\w-]+)(\s*=\s*)((?:\'[^\']*(?:\\.[^\']*)*\')|(?:\"[^\"]*(?:\\.[^\"]*)*\"))/ 
			, _replace: "$1<span class='attr_name'>$2</span>$3<span class='attr_value'>$4</span>"
			, _style: { attr_name:  "color: #b76900;", attr_value: "color: #ef2929;" }
		}
	}
}
