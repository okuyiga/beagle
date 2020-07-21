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

require_relative '../../Synthax/Variable'
require_relative '../base_component.rb'

class Style < BaseComponent

    def initialize
        edgeValue = EdgeValue.class.to_s
        textVariables = [
            Variable.new(:name => "backgroundColor", :typeName => "String", :isOptional => true),
            Variable.new(:name => "cornerRadius", :typeName => "String", :isOptional => true),
            Variable.new(:name => "size", :typeName => Size.new.name, :isOptional => true),
            Variable.new(:name => "margin", :typeName => edgeValue, :isOptional => true),
            Variable.new(:name => "padding", :typeName => edgeValue, :isOptional => true),
            Variable.new(:name => "position", :typeName => edgeValue, :isOptional => true),
            Variable.new(:name => "flex", :typeName => Flex.new.name, :isOptional => true),
            Variable.new(:name => "positionType", :typeName => PositionType.new.name, :isOptional => true),
            Variable.new(:name => "display", :typeName => Display.new.name, :isOptional => true)
        ]
        synthaxType = SynthaxType.new(:kind => 'struct', :name => self.name, :variables => textVariables)

        super(synthaxType)

    end

end