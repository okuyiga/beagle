#   Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA

#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
 
#       http://www.apache.org/licenses/LICENSE-2.0
 
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.

require_relative '../base_component.rb'
require_relative '../../Synthax/Attributes/field.rb'
require_relative '../../Synthax/Attributes/package.rb'
require_relative '../../Synthax/Types/built_in_type.rb'
require_relative '../../Models/Types/string.rb'
require_relative '../../Models/Types/double.rb'
require_relative '../../Models/Layout/position_type.rb'
require_relative 'edge_value.rb'
require_relative 'corner_radius.rb'
require_relative 'size.rb'
require_relative 'position_type.rb'
require_relative 'display.rb'
require_relative 'Flex/flex.rb'

class Style < BaseComponent

    def initialize
        edge_value = EdgeValue.new
        variables = [
            Field.new(
                :name => "backgroundColor",
                :type => TypeString.new,
                :isOptional => true,
                :comment => "Using a String parameter it sets the background color on this visual component.\nIt must be listed as an Hexadecimal color format without the \"#\".\nFor example, for a WHITE background type in \"FFFFFF\"."
            ),
            Field.new(
                :name => "cornerRadius",
                :type => CornerRadius.new,
                :isOptional => true,
                :comment => "Using a Double parameters it sets the corner of your view to make it round."
            ),
            Field.new(
                :name => "borderColor",
                :type => TypeString.new,
                :isOptional => true,
                :comment => "Sets the color of your view border. Supported formats:#RRGGBB[AA] and #RGB[A]."
            ),
            Field.new(
                :name => "borderWidth",
                :type => TypeDouble.new,
                :isOptional => true,
                :comment => "Sets the width of your view border."
            ),
            Field.new(
                :name => "size",
                :type => Size.new,
                :isOptional => true,
                :comment => "add size to current view applying the flex."
            ),
            Field.new(
                :name => "margin",
                :type => edge_value,
                :isOptional => true,
                :comment => "effects the spacing around the outside of a node.\nA node with margin will offset itself from the bounds of its parent\nbut also offset the location of any siblings.\nThe margin of a node contributes to the total size of its parent if the parent is auto sized."
            ),
            Field.new(
                :name => "padding",
                :type => edge_value,
                :isOptional => true,
                :comment => "affects the size of the node it is applied to.\nPadding in Yoga acts as if box-sizing: border-box; was set.\nThat is padding will not add to the total size of an element if it has an explicit size set.\nFor auto sized nodes padding will increase the size of the\nnode as well as offset the location of any children.."
            ),
            Field.new(
                :name => "position",
                :type => edge_value,
                :isOptional => true,
                :comment => "add padding to position."
            ),
            Field.new(
                :name => "flex",
                :type => Flex.new,
                :isOptional => true,
                :comment => ""
            ),
            Field.new(
                :name => "positionType",
                :type => PositionType.new,
                :isOptional => true,
                :comment => "The position type of an element defines how it is positioned within its parent."
            ),
            Field.new(
                :name => "display",
                :type => Display.new,
                :isOptional => true,
                :isBindable => true,
                :comment => "enables a flex context for all its direct children."
            )
            
        ]
        synthax_type = BuiltInType.new(
            :name => self.name,
            :variables => variables,
            :package => Package.new(:backend => "br.com.zup.beagle.core", :android => "br.com.zup.beagle.core"),
            :comment => "The style class will enable a few visual options to be changed.",
            :sameFileTypes => [CornerRadius.new, Display.new, PositionType.new]
        )

        super(synthax_type)

    end

end