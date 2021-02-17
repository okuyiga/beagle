/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import 'package:beagle/model/beagle_style.dart';
import 'package:beagle/render/beagle_flex_widget.dart';
import 'package:beagle/render/beagle_style_widget.dart';
import 'package:beagle/utils/color.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class BeaglePageIndicator extends StatelessWidget with StyleWidget {
  const BeaglePageIndicator({
    Key key,
    this.selectedColor,
    this.unselectedColor,
    this.numberOfPages,
    this.currentPage,
  }) : super(key: key);

  final String selectedColor;
  final String unselectedColor;
  final int numberOfPages;
  final int currentPage;

  static const double dotSpacing = 25;
  static const double dotSize = 8;

  Widget buildDot(int index) {
    return SizedBox(
      width: dotSpacing,
      child: Center(
        child: Material(
          color:
              HexColor(index == currentPage ? selectedColor : unselectedColor),
          type: MaterialType.circle,
          child: const SizedBox(
            width: dotSize,
            height: dotSize,
          ),
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    final flex = BeagleFlex(
      shrink: 0.0,
      flexDirection: FlexDirection.ROW,
      justifyContent: JustifyContent.CENTER,
    );
    final dots = List<Widget>.generate(numberOfPages, buildDot);
    return BeagleFlexWidget(
      flex: flex,
      children: dots,
    );
  }
}
