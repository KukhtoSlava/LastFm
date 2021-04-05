import argparse
from enum import Enum
import xml.etree.ElementTree as xml

_parser = argparse.ArgumentParser()
_parser.add_argument('name', type=str, nargs='?', default='default',
                     help='name attribute of the dimen')
_parser.add_argument('--value', '-v', type=float, default=0,
                     nargs='?', help='value of the dimen (float)')
_parser.add_argument('--pixel', '-p', type=str, choices=[
                     'dp', 'sp', 'pt', 'px', 'mm', 'in'], default='dp', nargs='?', help='pixel type of the dimen')
_parser.add_argument('--remove', '-rm', type=bool, default=False,
                     nargs='?', help='removes selected dimen')

_dimen_tag = 'dimen'
_name_attribute = 'name'


class CommentedTreeBuilder(xml.TreeBuilder):
    def comment(self, data):
        self.start(xml.Comment, {})
        self.data(data)
        self.end(xml.Comment)


class Dimen(Enum):
    DEFAULT = 1
    DP360 = 2
    DP600 = 3
    DP720 = 4

    def _get_file_path(self):
        return _dimen_xmls_folder_pattern.format(_dimen_xmls_folder_postfix[self])

    def _get_parsed_xml(self):
        parser = xml.XMLParser(target=CommentedTreeBuilder())
        return xml.parse(self._get_file_path(), parser)


_dimen_xmls_folder_postfix = {
    Dimen.DEFAULT: '',
    Dimen.DP360: '-sw360dp',
    Dimen.DP600: '-sw600dp',
    Dimen.DP720: '-sw720dp',
}
_dimen_value_multiplier = {
    Dimen.DEFAULT: 0.888888888888889,
    Dimen.DP360: 1,
    Dimen.DP600: 1.4,
    Dimen.DP720: 1.7,
}
_dimen_xmls_folder_pattern = 'androidApp/src/main/res/values{}/dimens.xml'


def _name_comparator(child):
    name = child.get(_name_attribute)
    if (name is None):
        name = ''
    return name


def _save_xml_to_file(xml_tree, file_path):
    root = xml_tree.getroot()
    root[:] = sorted(root, key=_name_comparator)
    last_word = None
    last_element = None
    for child in root:
        child.tail = '\n    '
        if (last_element is not None):
            name = child.get(_name_attribute)
            if (name is not None):
                word = name.split('_')[0]
                if(last_word is not None and last_word != word):
                    last_element.tail = '\n\n    '
                last_word = word
        last_element = child
    last_element.tail = '\n'
    xml_tree.write(file_path, encoding='utf-8', xml_declaration=True)
    return


def _find_dimen_by_name(root_element, name):
    for dimen in root_element.findall(_dimen_tag):
        if(dimen.get(_name_attribute) == name):
            return dimen


def _remove_dimen(name):
    for dimen in Dimen:
        parsed_xml = dimen._get_parsed_xml()
        root = parsed_xml.getroot()
        element = _find_dimen_by_name(root, name)
        if (element is not None):
            root.remove(element)
            _save_xml_to_file(parsed_xml, dimen._get_file_path())
    return


def _modify_or_add_dimen(name, value, pixel):
    for dimen in Dimen:
        parsed_xml = dimen._get_parsed_xml()
        root = parsed_xml.getroot()
        element = _find_dimen_by_name(root, name)
        text = '{0:g}'.format(value * _dimen_value_multiplier[dimen]) + pixel
        if (element is None):
            element = xml.Element(_dimen_tag, attrib={_name_attribute: name})
            root.append(element)
        element.text = text
        _save_xml_to_file(parsed_xml, dimen._get_file_path())
    return


if __name__ == '__main__':
    args = _parser.parse_args()
    name = args.name
    if (args.remove is None or args.remove):
        _remove_dimen(name)
    else:
        _modify_or_add_dimen(name, args.value, args.pixel)
    print('Done yopta!')
